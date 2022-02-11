package com.gajava.library.converter;

import com.gajava.library.dto.book.BookDto;
import com.gajava.library.model.Book;

public interface BookConverter {

    Book convertBookCreateDtoToEntity(BookDto dto);

    BookDto convertEntityToBookDto(Book entity);

    Book convertBookDtoToEntity(BookDto dto);

}
