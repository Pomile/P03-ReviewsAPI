package com.spring01.reviews.repository;
import com.spring01.reviews.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CommentRepository extends CrudRepository<Comment, Long> {
    Optional<Comment> findByReviewId(Long aLong);
}