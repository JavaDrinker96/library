package com.gajava.library.dto.author;

import com.gajava.library.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto extends BaseDto {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;

}
