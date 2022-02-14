package com.gajava.library.controller.dto.request.record;

import com.gajava.library.controller.dto.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RecordBookRequest implements Serializable {

    private Long bookId;
    private Pagination pagination;

}
