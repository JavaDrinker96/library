package com.gajava.library.exception;

public class EmptyNullParameterException extends RuntimeException {

    public EmptyNullParameterException() {
        super("Parameter can't be empty or null");
    }

}
