package com.gajava.library.exception;

public class AuthenticationPasswordException extends RuntimeException {

    public AuthenticationPasswordException() {
        super("The password is incorrect");
    }
}
