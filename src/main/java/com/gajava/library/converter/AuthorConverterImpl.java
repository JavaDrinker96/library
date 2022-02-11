package com.gajava.library.converter;

import com.gajava.library.dto.author.AuthorCreateDto;
import com.gajava.library.dto.author.AuthorDto;
import com.gajava.library.model.Author;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverterImpl implements AuthorConverter {

    private final ModelMapper modelMapper;

    public AuthorConverterImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Author convertAuthorCreateDtoToEntity(final AuthorCreateDto dto) {
        return modelMapper.map(dto, Author.class);
    }


    @Override
    public AuthorDto convertEntityToAuthorDto(final Author entity) {
        return modelMapper.map(entity, AuthorDto.class);
    }

    @Override
    public Author convertAuthorDtoToEntity(final AuthorDto dto) {
        return modelMapper.map(dto, Author.class);
    }

}
