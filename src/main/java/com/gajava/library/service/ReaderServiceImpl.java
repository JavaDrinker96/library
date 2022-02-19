package com.gajava.library.service;

import com.gajava.library.exception.LowReaderRatingException;
import com.gajava.library.exception.NoEntityException;
import com.gajava.library.exception.NoItemException;
import com.gajava.library.exception.UpdateEntityException;
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
import java.util.Optional;

@Service
public class ReaderServiceImpl extends AbstractService<Reader, ReaderRepository> implements ReaderService {

    final Integer LOWER_RATING_LIMIT = 30;
    final Integer RATING_PENALTY = 5;
    final String COMMENT_BEFORE_REFUND = null;

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
    public void borrowBook(final Long id, final Long bookId, final Integer rentalDays) {
        final Optional<Reader> optionalReader = repository.findById(id);
        final Reader reader = optionalReader.orElseThrow(() -> new NoEntityException(id, entityClass.getTypeName()));
        if (reader.getRating() < LOWER_RATING_LIMIT) {
            throw new LowReaderRatingException(reader.getId());
        }

        final Optional<Book> optionalBook = bookRepository.findById(bookId);
        final Book book = optionalBook.orElseThrow(() -> new NoEntityException(bookId, "Book"));

        reader.getBooks().add(book);
        final Optional<Reader> optionalUpdatedReader = Optional.of(repository.save(reader));
        final Reader updatedReader = optionalUpdatedReader.orElseThrow(() -> new UpdateEntityException(reader.getId(), entityClass.getTypeName()));

        final RentalRecord rentalRecord = new RentalRecord();
        rentalRecord.setBook(book);
        rentalRecord.setReader(updatedReader);
        rentalRecord.setRentalStartDate(LocalDate.now());
        rentalRecord.setRentalEndDate(LocalDate.now().plusDays(rentalDays));
        rentalRecord.setComment(COMMENT_BEFORE_REFUND);

        Optional.of(rentalRecordRepository.save(rentalRecord))
                .orElseThrow(() -> new UpdateEntityException(rentalRecord.getId(), "RentalRecord"));
    }

    @Override
    @Transactional
    public void refundBook(final Long id, final Long bookId, final String refundComment) {
        final Optional<Reader> optionalReader = repository.findById(id);
        final Reader reader = optionalReader.orElseThrow(() -> new NoEntityException(id, entityClass.getTypeName()));

        final Optional<Book> optionalBook = bookRepository.findById(bookId);
        final Book book = optionalBook.orElseThrow(() -> new NoEntityException(bookId, "Book"));

        final List<RentalRecord> rentalRecordsList = rentalRecordRepository.findRecordNotRefundByIds(id, bookId);
        if (rentalRecordsList.isEmpty()) {
            throw new NoEntityException("RentalRecord");
        }

        final Optional<Book> optionalUserRemoveBook = reader.getBooks().stream().filter(x -> x.getId().equals(bookId)).findFirst();
        final Book userRemoveBook = optionalUserRemoveBook.orElseThrow(() -> new NoItemException(entityClass.getTypeName(), "Book", bookId));

        reader.getBooks().remove(userRemoveBook);
        final RentalRecord rentalRecord = rentalRecordsList.get(0);
        rentalRecord.setActualReturnDate(LocalDate.now());
        if (rentalRecord.getActualReturnDate().isAfter(rentalRecord.getRentalEndDate())) {
            final Integer readerRating = reader.getRating();
            reader.setRating(readerRating - RATING_PENALTY);
        }
        final Optional<Reader> optionalUpdatedReader = Optional.of(repository.save(reader));
        if (optionalUpdatedReader.isEmpty()) {
            throw new UpdateEntityException(reader.getId(), entityClass.getTypeName());
        }

        final Integer bookQuantity = book.getQuantity();
        book.setQuantity(bookQuantity + 1);
        final Optional<Book> optionalUpdatedBook = Optional.of(bookRepository.save(book));
        if (optionalUpdatedBook.isEmpty()) {
            throw new UpdateEntityException(book.getId(), "Book");
        }

        rentalRecord.setComment(refundComment);
        final Optional<RentalRecord> optionalRentalRecord = Optional.of(rentalRecordRepository.save(rentalRecord));
        if (optionalRentalRecord.isEmpty()) {
            throw new UpdateEntityException(rentalRecord.getId(), "RentalRecord");
        }
    }

}
