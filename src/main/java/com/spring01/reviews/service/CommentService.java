package com.spring01.reviews.service;

import com.spring01.reviews.model.Comment;
import com.spring01.reviews.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implements the comment service create, read
 * information about comments.
 */
@Service
public class CommentService{

    @Autowired private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Create a comment
     * @param comment
     * @Return a comment
     * */

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    /**
     * Find a review comments
     * @param reviewId
     * @return list of comments
     * */
    public Optional<List<Comment>> findReviewComments(Long reviewId){
        return commentRepository.findAllByReviewId(reviewId);
    }

}