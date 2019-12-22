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
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.websocket.server.PathParam;

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

    @ExceptionHandler(value={ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadNumReqValidationExceptions(
            ConstraintViolationException ex) {
        StringBuilder msg = new StringBuilder();
        List<Map<String, String>> errors = new ArrayList<Map<String, String>>();
        for(ConstraintViolation<?> violation: ex.getConstraintViolations()){
            Map<String, String> err = new HashMap<String, String>();
            err.put( "" + violation.getInvalidValue(), violation.getMessage());
            msg.append(violation.getMessage());
            errors.add(err);
        }
        ErrorWrapper badRequest = new ErrorWrapper(HttpStatus.BAD_REQUEST, msg.toString(), errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(value={NumberFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadNum1ReqValidationExceptions(
            NumberFormatException ex) {
        String msg = "Type MisMatch. Path parameter must be an integer";
        List<String> errors = new ArrayList<String>();
        errors.add("Path parameter error " + ex.getMessage().toLowerCase());

        ErrorWrapper badRequest = new ErrorWrapper(HttpStatus.BAD_REQUEST, msg, errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(value={ResponseStatusException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundExceptions(
            ResponseStatusException ex) {
        String msg = ex.getReason();
        List<String> errors = new ArrayList<String>();
        errors.add(ex.getRootCause() + " - " + ex.getMessage());

        ErrorWrapper badRequest = new ErrorWrapper(HttpStatus.NOT_FOUND, msg, errors);
        return new ResponseEntity<>(badRequest, HttpStatus.NOT_FOUND );
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