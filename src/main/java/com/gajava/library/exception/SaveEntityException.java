package com.gajava.library.exception;

public class SaveEntityException extends RuntimeException {

    public SaveEntityException(final String entityType) {
        super("The " + entityType + " type entity wasn't save in the database");
    }

}
