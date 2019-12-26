package com.spring01.reviews.service;


import com.spring01.reviews.model.Review;
import com.spring01.reviews.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService{

    @Autowired private ReviewsRepository reviewsRepository;

    public ReviewService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    /**
     * Create a review
     * @param review
     * @Return a review
     * */
    public Review save(Review review){
        return reviewsRepository.save(review);
    }

    /**
     * Find a product reviews
     * @param productId
     * @Return a list of product reviews
     * @return*/
    public Optional<List<Review>> findProductReviews(Long productId){
        return reviewsRepository.findAllByProductId(productId);
    }
}