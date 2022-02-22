package com.gajava.library.exception;

public class AuthenticationLoginException extends RuntimeException{

    public AuthenticationLoginException() {
        super("There is no user with this username");
    }

}
