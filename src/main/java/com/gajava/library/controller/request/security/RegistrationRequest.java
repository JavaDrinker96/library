package com.gajava.library.controller.request.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@Validated
public class RegistrationRequest implements Serializable {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
