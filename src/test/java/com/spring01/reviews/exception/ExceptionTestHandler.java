package com.spring01.reviews.exception;

import com.spring01.reviews.model.Product;
import com.spring01.reviews.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;

@RestControllerAdvice
public class ExceptionTestHandler {
    @Autowired private ProductService productService;
    @ExceptionHandler(value={NullPointerException.class})
    public ResponseEntity<?> handleNullException() {
        Product product = new Product();
        product.setPrice(300.0);
        return new ResponseEntity<>(product, HttpStatus.OK );
    }}