package com.gajava.library.exception;

public class NoEntityException extends Exception {

    public NoEntityException() {
        super("The requested objects do not exist in the database");
    }

    public NoEntityException(Long entityId, String entityType) {
        super("A " + entityType + " type object with ID = " + entityId + " does not exist in the database");
    }

    public NoEntityException(String entityType) {
        super("The requested " + entityType + " type objects do not exist in the database");
    }

}
