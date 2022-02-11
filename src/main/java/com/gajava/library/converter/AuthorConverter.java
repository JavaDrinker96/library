package com.gajava.library.converter;

import com.gajava.library.dto.author.AuthorCreateDto;
import com.gajava.library.dto.author.AuthorDto;
import com.gajava.library.model.Author;

public interface AuthorConverter {

    Author convertAuthorCreateDtoToEntity(AuthorCreateDto dto);

    AuthorDto convertEntityToAuthorDto(Author entity);

    Author convertAuthorDtoToEntity(AuthorDto dto);

}
