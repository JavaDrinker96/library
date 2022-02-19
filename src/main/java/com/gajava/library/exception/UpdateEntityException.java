package com.gajava.library.exception;

public class UpdateEntityException extends RuntimeException {

    public UpdateEntityException(final Long entityId, final String entityType) {
        super("The " + entityType + " type entity with id =" + entityId + " wasn't update in the database");
    }

}
