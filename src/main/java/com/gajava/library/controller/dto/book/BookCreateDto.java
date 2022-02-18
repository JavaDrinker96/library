package com.gajava.library.controller.dto.book;

import com.gajava.library.controller.dto.BaseDto;
import com.gajava.library.controller.dto.author.AuthorDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookCreateDto extends BaseDto {

    private String title;
    private String genre;
    private LocalDate year;
    private String description;
    private Integer quantity;
    private Set<AuthorDto> authors;

}
