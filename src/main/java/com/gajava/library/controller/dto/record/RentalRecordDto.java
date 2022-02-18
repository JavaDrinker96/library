package com.gajava.library.controller.dto.record;

import com.gajava.library.controller.dto.BaseDto;
import com.gajava.library.controller.dto.book.BookDto;
import com.gajava.library.controller.dto.reader.ReaderDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RentalRecordDto extends BaseDto {

    private Long id;
    private BookDto book;
    private ReaderDto reader;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private LocalDate actualReturnDate;
    private String comment;

}
