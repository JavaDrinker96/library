package com.gajava.library.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("The transferred dto does not meet the requirements");
    }
}
