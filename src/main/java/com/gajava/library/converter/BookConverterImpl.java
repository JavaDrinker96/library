package com.gajava.library.converter;

import com.gajava.library.dto.book.BookDto;
import com.gajava.library.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookConverterImpl implements BookConverter {

    private final ModelMapper modelMapper;

    public BookConverterImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Book convertBookCreateDtoToEntity(final BookDto dto) {
        return modelMapper.map(dto, Book.class);
    }

    @Override
    public BookDto convertEntityToBookDto(final Book entity) {
        return modelMapper.map(entity, BookDto.class);
    }

    @Override
    public Book convertBookDtoToEntity(final BookDto dto) {
        return modelMapper.map(dto, Book.class);
    }
}
