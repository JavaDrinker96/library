package com.gajava.library.controller.request.book;

import com.gajava.library.controller.request.Pagination;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookRequest {

    private Pagination pagination;
    private BookFilter filter;
    private String contains;

}


