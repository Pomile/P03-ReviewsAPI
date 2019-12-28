package com.spring01.reviews.controller;

import com.spring01.reviews.config.DataConfig;
import com.spring01.reviews.model.Comment;
import com.spring01.reviews.model.Product;
import com.spring01.reviews.model.Review;
import com.spring01.reviews.repository.ReviewsRepository;
import com.spring01.reviews.service.CommentService;
import com.spring01.reviews.service.ReviewService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CommentsControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private JacksonTester<Comment> json;
    @MockBean private ReviewService reviewService;
    @MockBean private CommentService commentService;
    private AnnotationConfigApplicationContext configApplicationContext;
    private Comment comment;


    @Before
    public void setUp() throws Exception {
        configApplicationContext = new AnnotationConfigApplicationContext(DataConfig.class);
        Review review = configApplicationContext.getBean("reviewBean", Review.class);
        Comment comment = configApplicationContext.getBean("commentBean", Comment.class);
        this.comment = comment;
        review.setId(30L);
        comment.setId(1L);
        comment.setReviewId(30L);
        comment.setReview(review);
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(comment);

        given(reviewService.findReview(30L)).willReturn(java.util.Optional.of(review));
        given(commentService.save(any())).willReturn(comment);
    }

    @Test
    public void createCommentForReview() throws Exception {
        mvc.perform(post(new URI("/comments/reviews/30"))
                .content(json.write(comment).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L)
                );
    }
}