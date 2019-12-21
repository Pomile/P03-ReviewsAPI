package com.spring01.reviews.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
public class ErrorWrapper {
    private HttpStatus status;
    private String message;
    private List<Map<String, String>> errors;

    public ErrorWrapper(HttpStatus status, String message, List<Map<String, String>> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public  ErrorWrapper(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}