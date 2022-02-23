package com.gajava.library.service;

import com.gajava.library.exception.*;
import com.gajava.library.model.Book;
import com.gajava.library.model.Reader;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.repository.BookRepository;
import com.gajava.library.repository.ReaderRepository;
import com.gajava.library.repository.RentalRecordRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReaderServiceImpl extends AbstractService<Reader, ReaderRepository> implements ReaderService {

    final Integer LOWER_RATING_LIMIT = 30;
    final Integer RATING_PENALTY = 5;

    final BookRepository bookRepository;
    final RentalRecordRepository rentalRecordRepository;

    public ReaderServiceImpl(final ReaderRepository repository,
                             final BookRepository bookRepository,
                             final RentalRecordRepository rentalRecordRepository) {

        super(repository, Reader.class);
        this.bookRepository = bookRepository;
        this.rentalRecordRepository = rentalRecordRepository;
    }

    @Override
    @Transactional
    public void borrowBook(final Long readerId, final Long bookId, final Integer rentalDays) {
        if (Objects.isNull(readerId) || Objects.isNull(bookId) || Objects.isNull(rentalDays)) {
            throw new NullParameterException();
        }
        final Reader reader = findReaderById(readerId);
        checkReaderRating(reader);

        final Book book = findBookById(bookId);
        checkBookAvailability(book);
        book.setQuantity(book.getQuantity() - 1);
        updateBook(book);

        reader.getBooks().add(book);
        final Reader updatedReader = updateReader(reader);

        final RentalRecord record = prepareRecord(book, updatedReader, rentalDays);
        saveRecord(record);
    }

    @Override
    @Transactional
    public void refundBook(final Long readerId, final Long bookId, final String refundComment) {
        if (Objects.isNull(readerId) || Objects.isNull(bookId)) {
            throw new NullParameterException();
        }
        final Reader reader = findReaderById(readerId);
        final Book book = findBookById(bookId);

        final Book userRemovedBook = findRemovedBookOfReader(bookId, reader);
        reader.getBooks().remove(userRemovedBook);

        final RentalRecord rentalRecord = findOldestNotRefundRecord(readerId, bookId);
        rentalRecord.setActualReturnDate(LocalDate.now());

        tryChangeReaderRating(reader, rentalRecord);
        book.setQuantity(book.getQuantity() + 1);

        updateReader(reader);
        updateBook(book);

        rentalRecord.setComment(refundComment);
        updateRecord(rentalRecord);
    }

    private Reader findReaderById(final Long id) {
        return repository.findById(id).orElseThrow(() -> new NoEntityException(id, entityClass.getTypeName()));
    }

    private void checkReaderRating(final Reader reader) {
        if (reader.getRating() < LOWER_RATING_LIMIT) {
            throw new LowReaderRatingException(reader.getId());
        }
    }

    private void checkBookAvailability(final Book book) {
        if (book.getAvailability().equals(false)) {
            throw new BookNotAvailableException(book.getId());
        }
    }

    private Book findBookById(final Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoEntityException(id, "Book"));
    }

    private Book updateBook(final Book book) {
        return Optional.ofNullable(bookRepository.save(book))
                .orElseThrow(() -> new UpdateEntityException(book.getId(), Book.class.getTypeName()));
    }

    private Reader updateReader(final Reader reader) {
        return Optional.ofNullable(repository.save(reader))
                .orElseThrow(() -> new UpdateEntityException(reader.getId(), entityClass.getTypeName()));
    }

    private RentalRecord prepareRecord(final Book book, final Reader reader, final Integer rentalDays) {

        return RentalRecord.builder()
                .book(book)
                .reader(reader)
                .rentalStartDate(LocalDate.now())
                .rentalEndDate(LocalDate.now().plusDays(rentalDays))
                .build();
    }

    private RentalRecord saveRecord(final RentalRecord record) {
        return Optional.ofNullable(rentalRecordRepository.save(record))
                .orElseThrow(() -> new SaveEntityException(RentalRecord.class.getTypeName()));
    }

    private RentalRecord findOldestNotRefundRecord(final Long readerId, final Long bookId) {
        final List<RentalRecord> rentalRecordsList = rentalRecordRepository.findRecordsNotRefundByIds(readerId, bookId);
        if (rentalRecordsList.isEmpty()) {
            throw new NoEntityException(RentalRecord.class.getTypeName());
        }
        return rentalRecordsList.get(0);
    }

    private Book findRemovedBookOfReader(final Long bookId, final Reader reader) {
        return reader.getBooks().stream()
                .filter(x -> x.getId().equals(bookId)).findFirst()
                .orElseThrow(() -> new NoItemException(
                        entityClass.getTypeName(),
                        Book.class.getTypeName(),
                        bookId)
                );
    }

    private void tryChangeReaderRating(final Reader reader, final RentalRecord record) {
        if (record.getActualReturnDate().isAfter(record.getRentalEndDate())) {
            final Integer readerRating = reader.getRating();
            reader.setRating(readerRating - RATING_PENALTY);
        }
    }

    private RentalRecord updateRecord(final RentalRecord record) {
        return Optional.ofNullable(rentalRecordRepository.save(record))
                .orElseThrow(() -> new UpdateEntityException(record.getId(), RentalRecord.class.getTypeName()));
    }

}
