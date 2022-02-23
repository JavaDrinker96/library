package com.gajava.library.service;

import com.gajava.library.exception.LowReaderRatingException;
import com.gajava.library.exception.NoEntityException;
import com.gajava.library.exception.NoItemException;
import com.gajava.library.exception.UpdateEntityException;
import com.gajava.library.model.Author;
import com.gajava.library.model.Book;
import com.gajava.library.model.Reader;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.repository.BookRepository;
import com.gajava.library.repository.ReaderRepository;
import com.gajava.library.repository.RentalRecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReaderServiceTest {

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private RentalRecordRepository recordRepository;

    @InjectMocks
    private ReaderServiceImpl readerService;

    private Long readerId;
    private Long bookId;
    private Integer rentalDays;
    private Reader reader;
    private Book book;
    private String refundComment;

    private final Integer LOWER_RATING_LIMIT = 30;
    private final Integer RATING = 50;

    @Before
    public void setUp() {
        readerId = 1L;
        bookId = 1L;
        rentalDays = 3;
        refundComment = null;

        book = Book.builder()
                .id(bookId)
                .title("book")
                .genre("genre")
                .year(LocalDate.now())
                .description("description")
                .quantity(1)
                .availability(true)
                .authors(Set.of(new Author()))
                .build();

        final List<Book> books = new ArrayList();
        books.add(book);

        reader = Reader.builder()
                .id(readerId)
                .name("name")
                .surname("surname")
                .patronymic("patronymic")
                .libraryCardNumber("123456")
                .email("mail@mail.ru")
                .birthDate(LocalDate.now())
                .rating(RATING)
                .books(books)
                .phoneNumber("+375446452217")
                .build();
    }

    @Test(expected = NoEntityException.class)
    public void borrowBook_foundNullReader_throwException() {
        when(readerRepository.findById(readerId)).thenReturn(Optional.empty());
        readerService.borrowBook(readerId, bookId, rentalDays);
    }

    //TODO: when
    @Test(expected = LowReaderRatingException.class)
    public void borrowBook_readerWithLowRating_throwException() {
        reader.setRating(LOWER_RATING_LIMIT - 1);
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        readerService.borrowBook(readerId, bookId, rentalDays);
    }

    //TODO: when
    @Test(expected = NoEntityException.class)
    public void borrowBook_foundNullBook_throwException() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        readerService.borrowBook(readerId, bookId, rentalDays);
    }

    @Test(expected = UpdateEntityException.class)
    public void borrowBook_updateReturnNullBook_throwException() {
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        reader.setId(null);
        when(readerRepository.save(reader)).thenReturn(null);
        readerService.borrowBook(readerId, bookId, rentalDays);
    }

    @Test(expected = UpdateEntityException.class)
    public void borrowBook_updateReturnNullRecord_throwException() {
        final RentalRecord rentalRecord = new RentalRecord();
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        reader.setId(null);
        when(readerRepository.save(reader)).thenReturn(null);
        when(recordRepository.save(rentalRecord)).thenReturn(null);
        readerService.borrowBook(readerId, bookId, rentalDays);
    }

    @Test(expected = NoEntityException.class)
    public void refundBook_getNullReader_throwException() {
        when(readerRepository.findById(readerId)).thenReturn(Optional.empty());
        readerService.refundBook(readerId, bookId, refundComment);
    }

    @Test(expected = NoEntityException.class)
    public void refundBook_getNullBook_throwException() {
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        readerService.refundBook(readerId, bookId, refundComment);
    }

    @Test(expected = NoItemException.class)
    public void refundBook_notFoundUserRemovedBook_throwException(){
        final Long otherId = 2L;
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        book.setId(otherId);
        readerService.refundBook(readerId, bookId, refundComment);
    }

    @Test(expected = NoEntityException.class)
    public void refundBook_notFoundOldestNotRefundRecord_throwException(){
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(recordRepository.findRecordsNotRefundByIds(readerId, bookId)).thenReturn(new ArrayList<>());
        readerService.refundBook(readerId, bookId, refundComment);
    }

    @Test(expected = UpdateEntityException.class)
    public void refundBook_failedReaderUpdate_throwException(){
        final List<RentalRecord> recordList = new ArrayList<>();
        recordList.add(RentalRecord.builder().rentalEndDate(LocalDate.now().plusDays(1)).build());
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(recordRepository.findRecordsNotRefundByIds(readerId, bookId)).thenReturn(recordList);
        when(readerRepository.save(reader)).thenReturn(null);
        readerService.refundBook(readerId, bookId, refundComment);
    }

    @Test(expected = UpdateEntityException.class)
    public void refundBook_failedBookUpdate_throwException(){
        final List<RentalRecord> recordList = new ArrayList<>();
        recordList.add(RentalRecord.builder().rentalEndDate(LocalDate.now().plusDays(1)).build());
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(recordRepository.findRecordsNotRefundByIds(readerId, bookId)).thenReturn(recordList);
        when(readerRepository.save(reader)).thenReturn(reader);
        when(bookRepository.save(book)).thenReturn(null);
        readerService.refundBook(readerId, bookId, refundComment);
    }

    @Test(expected = UpdateEntityException.class)
    public void refundBook_failedRecordUpdate_throwException(){
        final List<RentalRecord> recordList = new ArrayList<>();
        final RentalRecord record = RentalRecord.builder()
                .rentalEndDate(LocalDate.now().plusDays(1))
                .build();

        recordList.add(RentalRecord.builder().rentalEndDate(LocalDate.now().plusDays(1)).build());
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(recordRepository.findRecordsNotRefundByIds(readerId, bookId)).thenReturn(recordList);
        when(readerRepository.save(reader)).thenReturn(reader);
        when(bookRepository.save(book)).thenReturn(book);
        when(recordRepository.save(record)).thenReturn(null);
        readerService.refundBook(readerId, bookId, refundComment);
    }

    @Test
    public void refundBook_AllRight(){
        final List<RentalRecord> recordList = new ArrayList<>();
        final RentalRecord record = RentalRecord.builder()
                .id(1L)
                .rentalStartDate(LocalDate.now())
                .rentalEndDate(LocalDate.now().plusDays(1))
                .build();
        recordList.add(record);

        recordList.add(RentalRecord.builder().rentalEndDate(LocalDate.now().plusDays(1)).build());
        when(readerRepository.findById(readerId)).thenReturn(Optional.ofNullable(reader));
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(recordRepository.findRecordsNotRefundByIds(readerId, bookId)).thenReturn(recordList);
        when(readerRepository.save(reader)).thenReturn(reader);
        when(bookRepository.save(book)).thenReturn(book);
        when(recordRepository.save(record)).thenReturn(record);
        readerService.refundBook(readerId, bookId, refundComment);
    }




}