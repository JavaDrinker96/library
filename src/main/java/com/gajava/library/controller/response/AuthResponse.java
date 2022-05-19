package com.gajava.library.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse implements Serializable {

    private String token;

}
