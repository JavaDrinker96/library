package com.gajava.library.service;

import com.gajava.library.exception.NoEntityException;
import com.gajava.library.exception.NullIdException;
import com.gajava.library.exception.SaveEntityException;
import com.gajava.library.exception.UpdateEntityException;
import com.gajava.library.model.Author;
import com.gajava.library.model.Book;
import com.gajava.library.repository.BookRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private BookServiceImpl bookService;


    private List<Book> expectedList;
    private Page<Book> page;
    private Book bookWithoutId;
    private Book book;
    private Author authorWithoutId;
    private String authorName;
    private String authorSurname;
    private String authorPatronymic;

    @Before
    public void setUp() throws Exception {
        authorName = "name";
        authorSurname = "surname";
        authorPatronymic = "patronymic";

        bookWithoutId = Book.builder()
                .title("book")
                .genre("genre")
                .year(LocalDate.now())
                .description("description")
                .quantity(1)
                .availability(true)
                .authors(Set.of(new Author()))
                .build();

        book = Book.builder()
                .id(1L)
                .title("book")
                .genre("genre")
                .year(LocalDate.now())
                .description("description")
                .quantity(1)
                .availability(true)
                .authors(Set.of(new Author()))
                .build();

        authorWithoutId = Author.builder()
                .name(authorName)
                .surname(authorSurname)
                .patronymic(authorPatronymic)
                .build();

        expectedList = List.of(bookWithoutId);
        page = new PageImpl(List.of(bookWithoutId));

    }

    @Test(expected = SaveEntityException.class)
    public void create_getNull_throwException() {
        when(bookRepository.save(bookWithoutId)).thenReturn(null);
        bookService.create(bookWithoutId);
    }

    @Test
    public void create_returnCreatedAuthor() {
        when(bookRepository.save(bookWithoutId)).thenReturn(book);
        final Book actualBook = bookService.create(bookWithoutId);
        Assertions.assertEquals(actualBook, book);
    }

    @Test(expected = NullIdException.class)
    public void update_nullId_throwException() {
        bookService.update(bookWithoutId);
    }

    @Test(expected = UpdateEntityException.class)
    public void update_getNull_throwException() {
        when(bookRepository.save(book)).thenReturn(null);
        bookService.update(book);
    }

    @Test
    public void update_returnUpdatedAuthor() {
        when(bookRepository.save(book)).thenReturn(book);
        final Book actualBook = bookService.update(book);
        Assertions.assertEquals(actualBook, book);
    }

    @Test(expected = NoEntityException.class)
    public void read_getNull_throwException() {
        final Long id = book.getId();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());
        bookService.read(id);
    }

    @Test
    public void read_returnAuthor() {
        final Long id = book.getId();
        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));
        final Book actualBook = bookService.read(id);
        Assertions.assertEquals(actualBook, book);
    }

    @Test(expected = NoEntityException.class)
    public void readAll_getEmptyList_throwException() {
        when(bookRepository.findAll(pageable)).thenReturn(Page.empty());
        bookService.readAll(pageable);
    }

    @Test
    public void readAll_returnAuthorList() {
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl(List.of(bookWithoutId)));
        final List<Book> actualList = bookService.readAll(pageable);
        final List<Book> expectedList = List.of(bookWithoutId);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test(expected = NoEntityException.class)
    public void getAvailableBooks_getEmptyList_throwException() {
        when(bookRepository.findByAvailabilityIsTrue(pageable)).thenReturn(Page.empty());
        bookService.getAvailableBooks(pageable);
    }

    @Test
    public void getAvailableBooks_returnBookList() {
        when(bookRepository.findByAvailabilityIsTrue(pageable)).thenReturn(page);
        final List<Book> actualList = bookService.getAvailableBooks(pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test(expected = NoEntityException.class)
    public void findByTitle_getEmptyList_throwException() {
        final String title = "title";
        when(bookRepository.findByTitleContaining(title, pageable)).thenReturn(Page.empty());
        bookService.findByTitle(title, pageable);
    }

    @Test
    public void findByTitle_returnBookList() {
        final String title = "title";
        when(bookRepository.findByTitleContaining(title, pageable)).thenReturn(page);
        final List<Book> actualList = bookService.findByTitle(title, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test(expected = NoEntityException.class)
    public void findByGenre_getEmptyList_throwException() {
        final String genre = "genre";
        when(bookRepository.findByGenreContaining(genre, pageable)).thenReturn(Page.empty());
        bookService.findByGenre(genre, pageable);
    }

    @Test
    public void findByGenre_returnBookList() {
        final String genre = "genre";
        when(bookRepository.findByGenreContaining(genre, pageable)).thenReturn(page);
        final List<Book> actualList = bookService.findByGenre(genre, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test(expected = NoEntityException.class)
    public void findByAuthor_getEmptyList_throwException() {
        when(bookRepository.findByAuthor(authorName, authorSurname, authorPatronymic, pageable))
                .thenReturn(Page.empty());
        bookService.findByAuthor(authorWithoutId, pageable);
    }

    @Test
    public void findByAuthor__returnBookList() {
        when(bookRepository.findByAuthor(authorName, authorSurname, authorPatronymic, pageable))
                .thenReturn(page);
        final List<Book> actualList = bookService.findByAuthor(authorWithoutId, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    public void findByAuthor_fullNameFieldsIsNull_returnBookList() {
        final Author author = new Author();

        when(bookRepository.findByAuthor("", "", "", pageable))
                .thenReturn(page);

        final List<Book> actualList = bookService.findByAuthor(author, pageable);
        Assertions.assertEquals(expectedList, actualList);
    }

}