package com.gajava.library.controller;

import com.gajava.library.controller.dto.author.AuthorCreateDto;
import com.gajava.library.controller.dto.author.AuthorDto;
import com.gajava.library.controller.request.Pagination;
import com.gajava.library.converter.AuthorConverter;
import com.gajava.library.model.Author;
import com.gajava.library.service.AuthorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorConverter converter;
    private final AuthorService authorService;

    public AuthorController(final AuthorConverter converter, final AuthorService authorService) {
        this.converter = converter;
        this.authorService = authorService;
    }

    @PostMapping(value = "create")
    public ResponseEntity<AuthorDto> create(@RequestBody final AuthorCreateDto createDto) {
        final Author author = converter.convertAuthorCreateDtoToEntity(createDto);
        final AuthorDto authorDto = converter.convertEntityToAuthorDto(authorService.create(author));
        return ResponseEntity.status(HttpStatus.CREATED).body(authorDto);
    }

    @GetMapping(value = "read/{id}")
    public ResponseEntity<AuthorDto> read(@PathVariable final Long id) {
        final AuthorDto authorDto = converter.convertEntityToAuthorDto(authorService.read(id));
        return ResponseEntity.status(HttpStatus.OK).body(authorDto);
    }

    @PutMapping(value = "update")
    public ResponseEntity<AuthorDto> update(@RequestBody final AuthorDto authorDto) {
        final Author author = converter.convertAuthorDtoToEntity(authorDto);
        final AuthorDto updatedAuthorDto = converter.convertEntityToAuthorDto(authorService.update(author));
        return ResponseEntity.status(HttpStatus.OK).body(updatedAuthorDto);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        authorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "read-all")
    public ResponseEntity<List<AuthorDto>> readAll(@RequestBody final Pagination request) {
        final PageRequest pageRequest = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(
                        request.getSorting().getDirection(),
                        request.getSorting().getProperty()
                )
        );

        final List<Author> authors = authorService.readAll(pageRequest);

        final List<AuthorDto> authorDtoList = authors.stream()
                .map(converter::convertEntityToAuthorDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(authorDtoList);
    }

}
