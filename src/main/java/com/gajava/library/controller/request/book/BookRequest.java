package com.gajava.library.controller.request.book;

import com.gajava.library.controller.request.Pagination;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@Validated
public class BookRequest {

    @NotNull
    private Pagination pagination;

    @NotNull
    private BookFilter filter;

    private String contains;

}


