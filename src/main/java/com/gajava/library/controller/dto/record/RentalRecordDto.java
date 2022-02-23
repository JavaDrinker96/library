package com.gajava.library.controller.dto.record;

import com.gajava.library.controller.dto.BaseDto;
import com.gajava.library.controller.dto.book.BookDto;
import com.gajava.library.controller.dto.reader.ReaderDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Validated
@NoArgsConstructor
public class RentalRecordDto extends BaseDto {

    @NotNull
    private Long id;

    @NotNull
    private BookDto book;

    @NotNull
    private ReaderDto reader;

    @NotNull
    private LocalDate rentalStartDate;

    @NotNull
    private LocalDate rentalEndDate;

    private LocalDate actualReturnDate;
    private String comment;

}
