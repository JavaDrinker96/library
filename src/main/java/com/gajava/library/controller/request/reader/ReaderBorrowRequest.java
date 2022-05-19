package com.gajava.library.controller.request.reader;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Validated
public class ReaderBorrowRequest implements Serializable {

    @NotNull
    private Long readerId;

    @NotNull
    private Long bookId;

    @NotNull
    private Integer rentalDays;

}
