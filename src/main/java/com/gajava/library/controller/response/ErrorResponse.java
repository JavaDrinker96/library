package com.gajava.library.controller.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse implements Serializable {

    private String error;
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;

}
