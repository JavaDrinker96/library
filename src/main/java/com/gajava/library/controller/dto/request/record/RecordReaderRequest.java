package com.gajava.library.controller.dto.request.record;

import com.gajava.library.controller.dto.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RecordReaderRequest implements Serializable {

    private Long readerId;
    private Pagination pagination;

}
