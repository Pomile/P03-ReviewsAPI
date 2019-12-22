package com.spring01.reviews.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ErrorWrapper {
    private HttpStatus status;
    private String message;
    private List<?> errors;


    public ErrorWrapper(HttpStatus status, String message, List<?> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }


    public  ErrorWrapper(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}