package com.gajava.library.dto.author;

import com.gajava.library.dto.BaseDto;
import com.gajava.library.dto.book.BookShortDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorDto extends BaseDto {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private List<BookShortDto> booksIds;

}
