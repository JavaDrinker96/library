package com.gajava.library.handler;

import com.gajava.library.controller.response.ErrorResponse;
import com.gajava.library.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDateTime.now;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(value = {NoEntityException.class})
    protected ResponseEntity<Object> handleNoEntityException(final NoEntityException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("NoEntityException")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoItemException.class)
    public ResponseEntity<ErrorResponse> handleNoItemException(final NoItemException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("NoItemException")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullParameterException.class)
    public ResponseEntity<ErrorResponse> handleEmptyNullParameterException(final NullParameterException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("EmptyNullParameterException")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullIdException.class)
    public ResponseEntity<ErrorResponse> handleNullIdException(final NullIdException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("NullIdException")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LowReaderRatingException.class)
    public ResponseEntity<ErrorResponse> handleLowReaderRatingException(final LowReaderRatingException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("LowReaderRatingException")
                .message(e.getMessage())
                .status(HttpStatus.BAD_GATEWAY)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(SaveEntityException.class)
    public ResponseEntity<ErrorResponse> handleSaveEntityException(final SaveEntityException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("SaveEntityException")
                .message(e.getMessage())
                .status(HttpStatus.BAD_GATEWAY)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(UpdateEntityException.class)
    public ResponseEntity<ErrorResponse> handleUpdateEntityException(final UpdateEntityException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("UpdateEntityException")
                .message(e.getMessage())
                .status(HttpStatus.BAD_GATEWAY)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadDtoException(final BadRequestException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("BadDtoException")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(final UsernameNotFoundException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("UsernameNotFoundException")
                .message(e.getMessage())
                .status(HttpStatus.BAD_GATEWAY)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(AuthenticationLoginException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationLoginException(final AuthenticationLoginException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("AuthenticationLoginException")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationPasswordException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationPasswordException(final AuthenticationPasswordException e) {
        final ErrorResponse response = ErrorResponse.builder()
                .error("AuthenticationPasswordException")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
