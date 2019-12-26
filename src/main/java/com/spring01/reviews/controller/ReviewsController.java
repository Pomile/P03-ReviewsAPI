package com.spring01.reviews.controller;

import com.spring01.reviews.model.Product;
import com.spring01.reviews.model.Review;
import org.springframework.web.server.ResponseStatusException;
import com.spring01.reviews.service.ProductService;
import com.spring01.reviews.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
@Validated
public class ReviewsController {

    // TODO: Wire JPA repositories here
    @Autowired private ReviewService reviewService;
    @Autowired private ProductService productService;

    public ReviewsController(ReviewService reviewService, ProductService productService) {
        this.reviewService = reviewService;
        this.productService = productService;
    }

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Object> createReviewForProduct(@Valid @RequestBody Review review, @PathVariable("productId") @Min(value = 1, message = "Product id must be greater than or to 1")Long productId) {
       Optional<Product> product = productService.findById(Math.toIntExact(productId));
       if (product.isPresent()){
           review.setProductId(productId);
           review.setProduct(product.get());
           Optional<Review> optionalReview = Optional.ofNullable(reviewService.save(review));
           if(optionalReview.isPresent()){

              return new ResponseEntity<Object>(optionalReview.get(), HttpStatus.CREATED);
           }
       }

       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<Object> listReviewsForProduct(@PathVariable("productId") Long productId) {
        Optional<Product> product = productService.findById(Math.toIntExact(productId));
        if (product.isPresent()){
            Optional<List<Review>> optionalReview = reviewService.findProductReviews(productId);
            return new ResponseEntity<Object>(optionalReview.get(), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }
}