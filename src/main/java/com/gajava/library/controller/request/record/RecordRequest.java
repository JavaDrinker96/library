package com.gajava.library.controller.request.record;

import com.gajava.library.controller.request.Pagination;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Validated
@Builder
public class RecordRequest {

    @NotNull
    private Pagination pagination;

    @NotNull
    private RecordFilter filter;

    private String contains;
    private LocalDate date;
    private Boolean refund;

}
