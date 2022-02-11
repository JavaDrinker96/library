package com.gajava.library.exception;

public class NoItemException extends Exception {

    public NoItemException(String parentType, String itemType, Long id) {
        super("A " + parentType + " type object does not have the required " + itemType + " type items with id = " + id);
    }
}
