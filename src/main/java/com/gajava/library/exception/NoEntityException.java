package com.gajava.library.exception;

public class NoEntityException extends RuntimeException {

    public NoEntityException() {
        super("The requested objects do not exist in the database");
    }

    public NoEntityException(final Long entityId, final String entityType) {
        super("A " + entityType + " type object with ID = " + entityId + " does not exist in the database");
    }

    public NoEntityException(final String entityType) {
        super("The requested " + entityType + " type objects do not exist in the database");
    }

}
