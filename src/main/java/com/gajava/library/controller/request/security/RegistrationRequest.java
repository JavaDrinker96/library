package com.gajava.library.controller.request.security;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RegistrationRequest implements Serializable {

    private String login;
    private String password;

}
