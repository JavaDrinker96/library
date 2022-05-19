package com.gajava.library.controller.dto.reader;

import com.gajava.library.controller.dto.BaseDto;
import com.gajava.library.controller.dto.book.BookDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Validated
@NoArgsConstructor
public class ReaderCreateDto extends BaseDto {

    @NotBlank
    private String fullName;

    @NotBlank
    @Length(min = 6, max = 6)
    private String libraryCardNumber;

    @NotBlank
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Integer rating;

    @NotNull
    private List<BookDto> books;

    @NotBlank
    private String phoneNumber;

}
