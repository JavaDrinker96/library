package com.gajava.library.exception;

public class UpdateEntityException extends Exception {

    public UpdateEntityException(Long entityId, String entityType) {
        super("The " + entityType + " type entity with id =" + entityId + " wasn't update in the database");
    }

}
