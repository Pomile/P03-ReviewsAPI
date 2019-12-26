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
import java.util.ArrayList;
import java.util.List;

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
        List<Review> reviews = new ArrayList<Review>();
        reviews.add(review);
        given(productRepository.findById(1L)).willReturn(java.util.Optional.of(product));
        given(reviewService.save(any())).willReturn(review);
        given(reviewService.findProductReviews(any())).willReturn(java.util.Optional.of(reviews));
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

    @Test
    public void shouldNotCreateReviewForProductWithEmptyTitle() throws Exception {
        review.setTitle("");
        mvc.perform(post(new URI("/reviews/products/1"))
                .content(json.write(review).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0].title").value("title cannot be empty")
                );
    }

    @Test
    public void shouldNotcreateReviewForProductWithEmptySummary() throws Exception {
        review.setSummary("");
        mvc.perform(post(new URI("/reviews/products/1"))
                .content(json.write(review).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0].summary").value("summary cannot be empty")
                );
    }

    @Test
    public void shouldNotcreateReviewForProductWithNegativeNumber() throws Exception {

        mvc.perform(post(new URI("/reviews/products/-1"))
                .content(json.write(review).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0].-1").value("Product id must be greater than or to 1")
                );
    }

    @Test
    public void shouldNotcreateReviewForProductWithInvalid() throws Exception {

        mvc.perform(post(new URI("/reviews/products/hhghgdh"))
                .content(json.write(review).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0]").value("Invalid input for /hhghgdh")
                );
    }

    @Test
    public void shouldFindProductReviews() throws Exception {

        mvc.perform(get(new URI("/reviews/products/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}