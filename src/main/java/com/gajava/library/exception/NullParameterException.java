package com.gajava.library.exception;

public class NullParameterException extends RuntimeException {

    public NullParameterException() {
        super("Parameter can't be null");
    }

}
