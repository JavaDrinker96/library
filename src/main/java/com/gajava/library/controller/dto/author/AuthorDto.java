package com.gajava.library.controller.dto.author;

import com.gajava.library.controller.dto.BaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@NoArgsConstructor
public class AuthorDto extends BaseDto {

    @NotNull
    private Long id;

    @NotBlank
    private String fullName;

}
