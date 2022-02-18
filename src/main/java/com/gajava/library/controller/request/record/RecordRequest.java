package com.gajava.library.controller.request.record;

import com.gajava.library.controller.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RecordRequest {

    private Pagination pagination;
    private RecordFilter filter;
    private String contains;
    private LocalDate date;
    private Boolean refund;

}
