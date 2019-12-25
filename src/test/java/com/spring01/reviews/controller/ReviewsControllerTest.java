package com.spring01.reviews.controller;

import com.spring01.reviews.config.DataConfig;
import com.spring01.reviews.model.Product;
import com.spring01.reviews.model.Review;
import com.spring01.reviews.repository.ProductRepository;
import com.spring01.reviews.service.ReviewService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ReviewsControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired private JacksonTester<Review> json;
    @MockBean
    ReviewService reviewService;
    @MockBean
    ProductRepository productRepository;
    private AnnotationConfigApplicationContext configApplicationContext;
    private Review review;

    @Before
    public void init(){
        configApplicationContext = new AnnotationConfigApplicationContext(DataConfig.class);
        Review review = configApplicationContext.getBean("reviewBean", Review.class);
        Product product = configApplicationContext.getBean("productBean", Product.class);
        this.review = review;
        review.setId(7L);
        product.setId(1L);
        review.setProduct(product);
        given(productRepository.findById(1L)).willReturn(java.util.Optional.of(product));
        given(reviewService.save(any())).willReturn(review);
    }
    @Test
    public void createReviewForProduct() throws Exception {
        mvc.perform(post(new URI("/reviews/products/1"))
                .content(json.write(review).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(7L)
        );
    }
}