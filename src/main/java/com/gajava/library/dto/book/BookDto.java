package com.gajava.library.dto.book;

import com.gajava.library.dto.BaseDto;
import com.gajava.library.dto.author.AuthorDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class BookDto extends BaseDto {

    private Long id;
    private String title;
    private String genre;
    private LocalDate year;
    private String description;
    private Integer quantity;
    private Boolean availability;
    private Set<AuthorDto> authors;

}
