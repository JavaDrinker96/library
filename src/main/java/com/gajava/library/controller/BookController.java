package com.gajava.library.controller;

import com.gajava.library.controller.dto.book.BookCreateDto;
import com.gajava.library.controller.dto.book.BookDto;
import com.gajava.library.controller.request.book.BookRequest;
import com.gajava.library.converter.BookConverter;
import com.gajava.library.manager.BookManagerImpl;
import com.gajava.library.model.Book;
import com.gajava.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookConverter bookConverter;
    private final BookService bookService;
    private final BookManagerImpl manager;

    @PostMapping(value = "create")
    public ResponseEntity<BookDto> create(@RequestBody final BookCreateDto createDto) {
        final Book book = bookConverter.convertBookCreateDtoToEntity(createDto);
        final BookDto bookDto = bookConverter.convertEntityToBookDto(bookService.create(book));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }

    @GetMapping(value = "read/{id}")
    public ResponseEntity<BookDto> read(@PathVariable final Long id) {
        final BookDto bookDto = bookConverter.convertEntityToBookDto(bookService.read(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @PutMapping(value = "update")
    public ResponseEntity<BookDto> update(@RequestBody final BookDto bookDto) {
        final Book book = bookConverter.convertBookDtoToEntity(bookDto);
        final BookDto updatedBookDto = bookConverter.convertEntityToBookDto(bookService.update(book));
        return ResponseEntity.status(HttpStatus.OK).body(updatedBookDto);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "read-all")
    public ResponseEntity<List<BookDto>> readAll(@RequestBody final BookRequest request) {
        final List<Book> bookList = manager.findByFilters(request);
        final List<BookDto> bookDtoList = bookList.stream()
                .map(bookConverter::convertEntityToBookDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(bookDtoList);
    }

}
