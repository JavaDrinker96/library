package com.gajava.library.controller.dto.author;

import com.gajava.library.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorCreateDto extends BaseEntity {

    private String name;
    private String surname;
    private String patronymic;

}
