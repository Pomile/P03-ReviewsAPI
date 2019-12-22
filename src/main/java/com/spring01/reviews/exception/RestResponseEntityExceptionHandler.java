package com.spring01.reviews.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleBadReqValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = new ArrayList<Map<String, String>>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            Map<String, String> error = new HashMap<>();
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            error.put(fieldName, errorMessage);
            errors.add(error);
        });
        ErrorWrapper badRequest = new ErrorWrapper(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(value
            = { DataIntegrityViolationException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<?> handleConflict(
            DataIntegrityViolationException ex, WebRequest request) {

        System.err.println(ex.getStackTrace());
        ErrorWrapper conflict = new ErrorWrapper(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        return new ResponseEntity<>(conflict, HttpStatus.CONFLICT);
    }
}