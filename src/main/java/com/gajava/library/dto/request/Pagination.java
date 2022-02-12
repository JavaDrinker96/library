package com.gajava.library.dto.request;

import com.gajava.library.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination extends BaseDto {

    private Integer page;
    private Integer size;

}
