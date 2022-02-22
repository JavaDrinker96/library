package com.gajava.library.exception;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(final Long id) {
        super(String.format("The requested book with id = %d is not available", id));
    }
}
