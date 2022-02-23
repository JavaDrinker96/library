package com.gajava.library.controller.dto.author;

import com.gajava.library.controller.dto.BaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@NoArgsConstructor
public class AuthorCreateDto extends BaseDto {

    @NotBlank
    private String fullName;

}
