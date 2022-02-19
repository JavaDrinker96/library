package com.gajava.library.exception;

public class NullIdException extends RuntimeException {

    public NullIdException(final String typeName) {
        super(String.format("Id for update %s type entity can't be null", typeName));
    }

}