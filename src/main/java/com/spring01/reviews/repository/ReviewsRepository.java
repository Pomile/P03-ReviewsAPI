package com.spring01.reviews.repository;
import com.spring01.reviews.model.Review;
import com.spring01.reviews.DAO.ReviewDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ReviewsRepository extends CrudRepository<Review, Long> {
    Optional<Review> findByProductId(Long aLong);
    Optional<List<Review>> findAllByProductId(Long aLong);
}