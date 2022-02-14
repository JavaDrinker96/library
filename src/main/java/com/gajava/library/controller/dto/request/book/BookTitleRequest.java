package com.gajava.library.controller.dto.request.book;

import com.gajava.library.controller.dto.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BookTitleRequest implements Serializable {

    private String title;
    private Pagination pagination;

}
