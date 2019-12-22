package com.spring01.reviews.controller;


import org.springframework.validation.annotation.Validated;
import com.spring01.reviews.model.Product;
import com.spring01.reviews.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
@Validated
class ProductsController {

    // TODO: Wire JPA repositories here
    @Autowired private ProductService productService;

    public void setProductService() {}

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a product.
     *
     * 1. Accept product as argument. Use {@link Valid} {@link RequestBody} annotations.
     * 2. Save product.
     * 3. return ResponseEntity with product and status
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product prod) {
        Optional<Product> product = Optional.ofNullable(productService.save(prod));
        if(product.isPresent()){
            return new ResponseEntity<Product>(product.get(), HttpStatus.CREATED);
        }
        throw new Error("Oops something went wrong");
    }

    /**
     * Finds a product by id.
     *
     * @param id The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @RequestMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findById(
            @PathVariable("id")
            @Min(value = 1, message="Invalid id")
                    Integer id) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()){
            return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
        }
        throw new  ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    /**
     * Finds a product by id.
     *
     * @param code The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @RequestMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findByCode(
            @RequestParam(name="code")
                    @Size(min = 3, message = "Invalid product code")
                    String code) {
        Optional<Product> optionalProduct = productService.findByProductCode(code);
        if (optionalProduct.isPresent()){
            return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
        }
        throw new  ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<?> listProducts() {
        System.out.println("Serving is running");
        throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
    }
}