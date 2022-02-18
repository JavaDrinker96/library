package com.gajava.library.controller.request.reader;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ReaderBorrowRequest implements Serializable {

    private Long readerId;
    private Long bookId;
    private Integer rentalDays;

}
