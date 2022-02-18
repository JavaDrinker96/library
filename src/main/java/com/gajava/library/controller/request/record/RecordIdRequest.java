package com.gajava.library.controller.request.record;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RecordIdRequest implements Serializable {

    private Long id;
    private RecordIdFilter idFilter;

}
