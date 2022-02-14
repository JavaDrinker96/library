package com.gajava.library.controller.dto.reader;

import com.gajava.library.controller.dto.BaseDto;
import com.gajava.library.controller.dto.book.BookDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReaderCreateDto extends BaseDto {

    private String name;
    private String surname;
    private String patronymic;
    private Integer libraryCardNumber;
    private String email;
    private LocalDate birthDate;
    private Integer rating;
    private List<BookDto> books;
    private String phoneNumber;

}
