package com.gajava.library.controller;

import com.gajava.library.controller.dto.book.BookCreateDto;
import com.gajava.library.controller.dto.book.BookDto;
import com.gajava.library.controller.dto.request.Pagination;
import com.gajava.library.controller.dto.request.book.BookAuthorRequest;
import com.gajava.library.controller.dto.request.book.BookGenreRequest;
import com.gajava.library.controller.dto.request.book.BookTitleRequest;
import com.gajava.library.converter.BookConverter;
import com.gajava.library.model.Author;
import com.gajava.library.model.Book;
import com.gajava.library.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    private final ModelMapper modelMapper;
    private final BookConverter converter;
    private final BookService bookService;

    public BookController(final ModelMapper modelMapper,
                          final BookConverter converter,
                          final BookService bookService) {

        this.modelMapper = modelMapper;
        this.converter = converter;
        this.bookService = bookService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<BookDto> create(@RequestBody final BookCreateDto createDto) {
        final Book book = converter.convertBookCreateDtoToEntity(createDto);
        final BookDto bookDto = converter.convertEntityToBookDto(bookService.create(book));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<BookDto> read(@PathVariable final Long id) {
        final BookDto bookDto = converter.convertEntityToBookDto(bookService.read(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<BookDto> update(@RequestBody final BookDto bookDto) {
        final Book book = converter.convertBookDtoToEntity(bookDto);
        final BookDto updatedBookDto = converter.convertEntityToBookDto(bookService.update(book));
        return ResponseEntity.status(HttpStatus.OK).body(updatedBookDto);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/read-all")
    public ResponseEntity<List<BookDto>> readAll(@RequestBody final Pagination pagination) {
        final List<Book> books = bookService
                .readAll(PageRequest.of(pagination.getPage(), pagination.getSize()));

        final List<BookDto> bookDtoList = books.stream()
                .map(converter::convertEntityToBookDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(bookDtoList);
    }

    @GetMapping(value = "/by-available")
    public ResponseEntity<List<BookDto>> readAllAvailable(@RequestBody final Pagination pagination) {
        final List<Book> books = bookService
                .getAvailableBooks(PageRequest.of(pagination.getPage(), pagination.getSize()));

        final List<BookDto> bookDtoList = books.stream()
                .map(converter::convertEntityToBookDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(bookDtoList);
    }


    @GetMapping(value = "/by-title")
    public ResponseEntity<List<BookDto>> readByTitle(@RequestBody final BookTitleRequest request) {
        final List<Book> books = bookService
                .findByTitle(request.getTitle(),
                        PageRequest.of(
                                request.getPagination().getPage(),
                                request.getPagination().getSize()));

        final List<BookDto> bookDtoList = books.stream()
                .map(converter::convertEntityToBookDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(bookDtoList);
    }

    @GetMapping(value = "/by-genre")
    public ResponseEntity<List<BookDto>> readByGenre(@RequestBody final BookGenreRequest request) {
        final List<Book> books = bookService
                .findByGenre(request.getGenre(),
                        PageRequest.of(
                                request.getPagination().getPage(),
                                request.getPagination().getSize()));

        final List<BookDto> bookDtoList = books.stream()
                .map(converter::convertEntityToBookDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(bookDtoList);
    }

    @GetMapping(value = "/by-author")
    public ResponseEntity<List<BookDto>> readByAuthor(@RequestBody final BookAuthorRequest request) {
        final Author author = modelMapper.map(request.getAuthor(), Author.class);
        final Integer page = request.getPagination().getPage();
        final Integer size = request.getPagination().getSize();
        final Boolean expandSearch = request.getExpandSearch();

        final List<Book> books = bookService
                .findByAuthor(author, PageRequest.of(page, size), expandSearch);

        final List<BookDto> bookDtoList = books.stream()
                .map(converter::convertEntityToBookDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(bookDtoList);
    }

}
