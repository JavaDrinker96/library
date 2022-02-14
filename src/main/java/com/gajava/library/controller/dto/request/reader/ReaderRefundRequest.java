package com.gajava.library.controller.dto.request.reader;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ReaderRefundRequest implements Serializable {

    private Long readerId;
    private Long bookId;
    private String refundComment;

}
