package com.gajava.library.dto.reader;

import com.gajava.library.dto.BaseDto;
import com.gajava.library.dto.book.BookDto;
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
