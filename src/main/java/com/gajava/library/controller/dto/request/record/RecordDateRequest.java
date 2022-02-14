package com.gajava.library.controller.dto.request.record;

import com.gajava.library.controller.dto.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class RecordDateRequest implements Serializable {

    private LocalDate date;
    private Pagination pagination;

}
