package com.gajava.library.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Validated
@Builder
public class Pagination implements Serializable {

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    @NotNull
    private Sorting sorting;

}
