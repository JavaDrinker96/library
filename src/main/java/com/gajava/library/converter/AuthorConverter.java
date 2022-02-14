package com.gajava.library.converter;

import com.gajava.library.controller.dto.author.AuthorCreateDto;
import com.gajava.library.controller.dto.author.AuthorDto;
import com.gajava.library.model.Author;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {

    private final ModelMapper modelMapper;

    public AuthorConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Author convertAuthorCreateDtoToEntity(final AuthorCreateDto dto) {
        return modelMapper.map(dto, Author.class);
    }


    public AuthorDto convertEntityToAuthorDto(final Author entity) {
        return modelMapper.map(entity, AuthorDto.class);
    }

    public Author convertAuthorDtoToEntity(final AuthorDto dto) {
        return modelMapper.map(dto, Author.class);
    }

}
