package com.gajava.library.service;

import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.Author;
import com.gajava.library.model.Book;
import com.gajava.library.model.Reader;
import com.gajava.library.model.RentalRecord;
import com.gajava.library.repository.RentalRecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class RentalRecordServiceTest {

    @Mock
    private RentalRecordRepository recordRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private RentalRecordServiceImpl recordService;

    private Book book;
    private RentalRecord record;
    private Page<RentalRecord> page;
    private Reader reader;
    private List<RentalRecord> expectedList;
    private Long readerId;
    private Long bookId;
    private String readerName;
    private String readerSurname;
    private String readerPatronymic;
    private LocalDate date;

    @Before
    public void setUp() throws Exception {

        readerId = 1L;
        readerName = "name";
        readerSurname = "surname";
        readerPatronymic = "patronymic";

        bookId = 1L;
        date = LocalDate.now();

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
                .name(readerName)
                .surname(readerSurname)
                .patronymic(readerPatronymic)
                .libraryCardNumber("123456")
                .email("mail@mail.ru")
                .birthDate(date)
                .rating(50)
                .books(books)
                .phoneNumber("+375446452217")
                .build();

        record = RentalRecord.builder()
                .id(1L)
                .book(book)
                .reader(reader)
                .rentalStartDate(date)
                .rentalEndDate(date.plusDays(1))
                .build();

        page = new PageImpl(List.of(record));
        expectedList = List.of(record);
    }

    @Test(expected = NoEntityException.class)
    public void findByReader_getEmptyList_throwException() {
        when(recordRepository.findByReader(readerName, readerSurname, readerPatronymic, pageable))
                .thenReturn(Page.empty());
        recordService.findByReader(reader, pageable);
    }

    @Test
    public void findByReader_fullNameFieldsIsNull_returnRecordList() {
        final Reader reader = new Reader();
        when(recordRepository.findByReader("", "", "", pageable))
                .thenReturn(page);
        final List<RentalRecord> actualList = recordService.findByReader(reader, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    public void findByReader_returnRecordList() {
        when(recordRepository.findByReader(readerName, readerSurname, readerPatronymic, pageable))
                .thenReturn(page);
        final List<RentalRecord> actualList = recordService.findByReader(reader, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test(expected = NoEntityException.class)
    public void findByBook_getEmptyList_throwException() {
        final String title = "title";
        when(recordRepository.findByBook(title, pageable)).thenReturn(Page.empty());
        recordService.findByBook(title, pageable);
    }

    @Test
    public void findByBook_returnRecordList() {
        final String title = "title";
        when(recordRepository.findByBook(title, pageable)).thenReturn(page);
        final List<RentalRecord> actualList = recordService.findByBook(title, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test(expected = NoEntityException.class)
    public void findByBookId_notFoundRecord_throwException() {
        when(recordRepository.findByBookId(bookId)).thenReturn(null);
        recordService.findByBookId(bookId);
    }

    @Test
    public void findByBookId_returnRecord() {
        when(recordRepository.findByBookId(bookId)).thenReturn(record);
        final RentalRecord actualRecord = recordService.findByBookId(bookId);
        Assertions.assertEquals(record, actualRecord);
    }

    @Test(expected = NoEntityException.class)
    public void findByReaderId_notFoundRecord_throwException() {
        when(recordRepository.findByReaderId(readerId)).thenReturn(null);
        recordService.findByBookId(readerId);
    }

    @Test
    public void findByReaderId_returnRecord() {
        when(recordRepository.findByBookId(readerId)).thenReturn(record);
        final RentalRecord actualRecord = recordService.findByBookId(readerId);
        Assertions.assertEquals(record, actualRecord);
    }

    @Test(expected = NoEntityException.class)
    public void findByTakeAfter_getEmptyList_throwException() {
        when(recordRepository.findByRentalStartDateAfter(date, pageable)).thenReturn(Page.empty());
        recordService.findByTakeAfter(date, pageable);
    }

    @Test
    public void findByTakeAfter_returnRecordList() {
        when(recordRepository.findByRentalStartDateAfter(date.plusDays(-1), pageable)).thenReturn(page);
        final List<RentalRecord> actualList = recordService.findByTakeAfter(date.plusDays(-1), pageable);
        record.setRentalStartDate(date.plusDays(-1));
        final List<RentalRecord> expectedList = List.of(record);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test(expected = NoEntityException.class)
    public void findByReturnAfter_getEmptyList_throwException() {
        when(recordRepository.findByActualReturnDateAfter(date, pageable)).thenReturn(Page.empty());
        recordService.findByReturnAfter(date, pageable);
    }

    @Test
    public void findByReturnAfter_returnRecordList() {
        when(recordRepository.findByActualReturnDateAfter(date, pageable))
                .thenReturn(new PageImpl(List.of(record)));
        final List<RentalRecord> actualList = recordService.findByReturnAfter(date, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test(expected = NoEntityException.class)
    public void findByRentalStatus_refundIsTrue_getEmptyList_throwException() {
        when(recordRepository.findByActualReturnDateIsNotNull(pageable)).thenReturn(Page.empty());
        recordService.findByRentalStatus(true, pageable);
    }

    @Test
    public void findByRentalStatus_refundIsTrue_returnRecordList() {
        when(recordRepository.findByActualReturnDateIsNotNull(pageable)).thenReturn(page);
        final List<RentalRecord> actualList = recordService.findByRentalStatus(true, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test(expected = NoEntityException.class)
    public void findByRentalStatus_refundIsFalse_getEmptyList_throwException() {
        when(recordRepository.findByActualReturnDateIsNull(pageable)).thenReturn(Page.empty());
        recordService.findByRentalStatus(false, pageable);
    }

    @Test
    public void findByRentalStatus_refundIsFalse_returnRecordList() {
        when(recordRepository.findByActualReturnDateIsNull(pageable)).thenReturn(page);
        final List<RentalRecord> actualList = recordService.findByRentalStatus(false, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

}