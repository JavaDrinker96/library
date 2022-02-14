package com.gajava.library.exception;

public class LowReaderRatingException extends RuntimeException {

    public LowReaderRatingException(final Long id) {
        super("A reader with id = " + id + " has a low rating for this action");
    }

}
