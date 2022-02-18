package com.gajava.library.controller.dto.author;

import com.gajava.library.controller.dto.BaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDto extends BaseDto {

    private Long id;
    private String fullName;

}
