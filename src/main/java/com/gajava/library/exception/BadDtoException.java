package com.gajava.library.exception;

public class BadDtoException extends RuntimeException {

    public BadDtoException() {
        super("The transferred dto does not meet the requirements");
    }
}
