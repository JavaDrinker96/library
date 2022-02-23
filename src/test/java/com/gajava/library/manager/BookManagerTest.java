package com.gajava.library.manager;


import com.gajava.library.controller.request.Pagination;
import com.gajava.library.controller.request.Sorting;
import com.gajava.library.controller.request.book.BookFilter;
import com.gajava.library.controller.request.book.BookRequest;
import com.gajava.library.exception.BadRequestException;
import com.gajava.library.exception.NoEntityException;
import com.gajava.library.model.Author;
import com.gajava.library.model.Book;
import com.gajava.library.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookManagerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookManagerImpl bookManager;

    private BookRequest request;
    private PageRequest pageable;
    private Book book;
    private List<Book> expectedList;

    @Before
    public void setUp() throws Exception {
        final Integer pageNumber = 0;
        final Integer pageSize = 4;
        final Direction direction = Direction.ASC;
        final String sortingField = "id";

        final Sorting sorting = Sorting.builder().direction(direction).property(sortingField).build();
        final Pagination pagination = Pagination.builder().page(pageNumber).size(pageSize).sorting(sorting).build();
        request = BookRequest.builder().pagination(pagination).build();
        pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortingField));

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

        expectedList = List.of(book);
    }

    @Test(expected = NoEntityException.class)
    public void findByFilters_filterIsAvailable_getEmptyList_throwException() {
        request.setFilter(BookFilter.AVAILABLE);
        when(bookService.getAvailableBooks(pageable)).thenReturn(Collections.emptyList());
        bookManager.findByFilters(request);
        verify(bookService).getAvailableBooks(pageable);
    }

    @Test
    public void findByFilters_filterIsAvailable_getBookList() {
        request.setFilter(BookFilter.AVAILABLE);
        when(bookService.getAvailableBooks(pageable)).thenReturn(expectedList);
        bookManager.findByFilters(request);
        verify(bookService).getAvailableBooks(pageable);
    }

    @Test(expected = NoEntityException.class)
    public void findByFilters_filterIsTitle_getEmptyList_throwException() {
        final String title = "title";
        request.setFilter(BookFilter.TITLE);
        request.setContains(title);
        when(bookService.findByTitle(title, pageable)).thenReturn(Collections.emptyList());
        bookManager.findByFilters(request);
        verify(bookService).findByTitle(title, pageable);
    }

    @Test
    public void findByFilters_filterIsTitle_getBookList() {
        final String title = "title";
        request.setFilter(BookFilter.TITLE);
        request.setContains(title);
        when(bookService.findByTitle(title, pageable)).thenReturn(expectedList);
        bookManager.findByFilters(request);
        verify(bookService).findByTitle(title, pageable);
    }

    @Test(expected = NoEntityException.class)
    public void findByFilters_filterIsGenre_getEmptyList_throwException() {
        final String genre = "genre";
        request.setFilter(BookFilter.GENRE);
        request.setContains(genre);
        when(bookService.findByGenre(genre, pageable)).thenReturn(Collections.emptyList());
        bookManager.findByFilters(request);
        verify(bookService).findByGenre(genre, pageable);
    }

    @Test
    public void findByFilters_filterIsGenre_getBookList() {
        final String genre = "genre";
        request.setFilter(BookFilter.GENRE);
        request.setContains(genre);
        when(bookService.findByGenre(genre, pageable)).thenReturn(expectedList);
        bookManager.findByFilters(request);
        verify(bookService).findByGenre(genre, pageable);
    }

    @Test(expected = BadRequestException.class)
    public void findByFilters_filterIsAuthor_getNullContains_throwException() {
        request.setFilter(BookFilter.AUTHOR);
        bookManager.findByFilters(request);
    }

    @Test(expected = BadRequestException.class)
    public void findByFilters_filterIsAuthor_getToShortContains_throwException() {
        final String contains = "";
        request.setFilter(BookFilter.AUTHOR);
        request.setContains(contains);
        bookManager.findByFilters(request);
    }

    @Test(expected = BadRequestException.class)
    public void findByFilters_filterIsAuthor_getToLongContains_throwException() {
        final String contains = "name surname patronymic other";
        request.setFilter(BookFilter.AUTHOR);
        request.setContains(contains);
        bookManager.findByFilters(request);
    }

    @Test
    public void findByFilters_filterIsAuthor_returnAuthorList() {
        final String contains = "name surname patronymic";
        final Author author = Author.builder()
                .name("name")
                .surname("surname")
                .patronymic("patronymic")
                .build();

        request.setFilter(BookFilter.AUTHOR);
        request.setContains(contains);
        when(bookService.findByAuthor(author, pageable)).thenReturn(List.of(book));
        bookManager.findByFilters(request);
        verify(bookService).findByAuthor(author, pageable);
    }

    @Test
    public void findByFilters_filterIsNone_returnAuthorList(){
        request.setFilter(BookFilter.NONE);
        when(bookService.readAll(pageable)).thenReturn(List.of(book));
        bookManager.findByFilters(request);
        verify(bookService).readAll(pageable);
    }

}