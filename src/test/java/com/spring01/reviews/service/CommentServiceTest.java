package com.spring01.reviews.service;

import com.spring01.reviews.config.DataConfig;
import com.spring01.reviews.model.Comment;
import com.spring01.reviews.model.Product;
import com.spring01.reviews.model.Review;
import com.spring01.reviews.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CommentService.class})
public class CommentServiceTest {
    @MockBean private CommentRepository commentRepository;
    @Autowired private CommentService commentService;
    private AnnotationConfigApplicationContext configApplicationContext;
    private Comment comment;

    @Before
    public void setUp() throws Exception {
        configApplicationContext = new AnnotationConfigApplicationContext(DataConfig.class);
        Review review = configApplicationContext.getBean("reviewBean", Review.class);
        Comment comment = configApplicationContext.getBean("commentBean", Comment.class);
        review.setId(1L);
        comment.setReview(review);
        comment.setReviewId(1L);
        this.comment = comment;
    }

    @Test
    public void save() {
        Comment newComment = comment;
       newComment.setId(2L);
        Mockito.when(
                commentRepository.save(any()))
                .thenReturn(comment);
        Comment commentRev = commentService.save(newComment);
        assertEquals(newComment.getId(), comment.getId());
    }
}