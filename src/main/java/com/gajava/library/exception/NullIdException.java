package com.gajava.library.exception;

public class NullIdException extends Exception {

    public NullIdException(String typeName) {
        super(String.format("Id for update %s type entity can't be null", typeName));
    }

}