package com.gajava.library.controller.dto.request.book;

import com.gajava.library.controller.dto.author.AuthorCreateDto;
import com.gajava.library.controller.dto.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BookAuthorRequest implements Serializable {

    private AuthorCreateDto author;
    private Pagination pagination;
    private Boolean expandSearch;

}
