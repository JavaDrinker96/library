package com.gajava.library.converter;

import com.gajava.library.controller.dto.book.BookCreateDto;
import com.gajava.library.controller.dto.book.BookDto;
import com.gajava.library.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookConverter  {

    private final ModelMapper modelMapper;

    public BookConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Book convertBookCreateDtoToEntity(final BookCreateDto dto) {
        return modelMapper.map(dto, Book.class);
    }

    public BookDto convertEntityToBookDto(final Book entity) {
        return modelMapper.map(entity, BookDto.class);
    }

    public Book convertBookDtoToEntity(final BookDto dto) {
        return modelMapper.map(dto, Book.class);
    }
}
