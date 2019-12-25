package com.spring01.reviews.service;

import com.spring01.reviews.config.DataConfig;
import com.spring01.reviews.model.Product;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring01.reviews.model.Review;
import com.spring01.reviews.repository.ReviewsRepository;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ReviewService.class})
public class ReviewServiceTest {
    @MockBean private ReviewsRepository reviewsRepository;
    @Autowired private ReviewService reviewService;
    private AnnotationConfigApplicationContext configApplicationContext;
    private Review rev;

    @Before
    public void setup(){
        configApplicationContext = new AnnotationConfigApplicationContext(DataConfig.class);
        Review review = configApplicationContext.getBean("reviewBean", Review.class);
        Product product = configApplicationContext.getBean("productBean", Product.class);
        review.setProductId(1L);
        review.setProduct(product);
        this.rev=review;
    }
    @Test
    public void save() {
        Review newReview = rev;
        newReview.setId(6L);
        Mockito.when(
                reviewsRepository.save(any()))
                .thenReturn(newReview);
        Review saveRev = reviewService.save(rev);
       assertEquals(newReview.getId(), saveRev.getId());
    }
}