package com.gajava.library.exception;

public class SaveEntityException extends Exception {

    public SaveEntityException(String entityType) {
        super("The " + entityType + " type entity wasn't save in the database");
    }

}