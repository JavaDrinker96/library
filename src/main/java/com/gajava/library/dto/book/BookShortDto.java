package com.gajava.library.dto.book;

import com.gajava.library.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookShortDto extends BaseDto {

    private Long id;
    private String title;
    private String genre;
    private LocalDate year;
    private String description;
    private Integer quantity;
    private Boolean availability;

}