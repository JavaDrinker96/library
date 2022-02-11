package com.gajava.library.dto.record;

import com.gajava.library.dto.BaseDto;
import com.gajava.library.dto.book.BookDto;
import com.gajava.library.dto.reader.ReaderDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalRecordDto extends BaseDto {

    private Long id;
    private BookDto book;
    private ReaderDto reader;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private LocalDate actualReturnDate;
    private String comment;

}
