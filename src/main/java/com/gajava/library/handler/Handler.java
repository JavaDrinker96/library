package com.gajava.library.handler;

import com.gajava.library.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(NoEntityException.class)
    public ResponseEntity<String> handleNoEntityException(final NoEntityException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NoItemException.class)
    public ResponseEntity<String> handleNoItemException(final NoItemException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(EmptyNullParameterException.class)
    public ResponseEntity<String> handleEmptyNullParameterException(final EmptyNullParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NullIdException.class)
    public ResponseEntity<String> handleNullIdException(final NullIdException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(LowReaderRatingException.class)
    public ResponseEntity<String> handleLowReaderRatingException(final LowReaderRatingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(SaveEntityException.class)
    public ResponseEntity<String> handleSaveEntityException(final SaveEntityException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    }

    @ExceptionHandler(UpdateEntityException.class)
    public ResponseEntity<String> handleUpdateEntityException(final UpdateEntityException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    }

}
