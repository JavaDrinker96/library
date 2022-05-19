package com.gajava.library.exception;

public class NoItemException extends RuntimeException {

    public NoItemException(final String parentType, final String itemType, final Long id) {
        super("A " + parentType + " type object does not have the required " + itemType + " type items with id = " + id);
    }
}
