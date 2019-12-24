package com.spring01.reviews.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ParameterError {
    private ZonedDateTime timestamp;
    private HttpStatus status;
    private String message;
    private String error;


    public ParameterError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = ZonedDateTime.now();
    }

}