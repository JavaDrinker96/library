package com.gajava.library.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Builder
public class Sorting {

    @NotNull
    private Direction direction;

    @NotNull
    private String property;

}
