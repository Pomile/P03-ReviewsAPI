package com.spring01.reviews.repository;
import com.spring01.reviews.config.DataConfig;
import com.spring01.reviews.model.Comment;
import com.spring01.reviews.model.Product;
import com.spring01.reviews.model.Review;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentRepositoryTest {
    // private SessionFactory sessionFactory;
    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TestEntityManager testEntityManager;
    @Autowired private ReviewsRepository reviewsRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private ProductRepository productRepository;
    private AnnotationConfigApplicationContext configApplicationContext;
    private Review review;
    private Long reviewId;
    private Comment comment;
    @Before
    public void setUp() throws Exception {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        System.err.println("Connected to" + con.getMetaData().getURL());
        configApplicationContext = new AnnotationConfigApplicationContext(DataConfig.class);
        Product product = configApplicationContext.getBean("productBean", Product.class);
        Review review = configApplicationContext.getBean("reviewBean", Review.class);
        Comment comment = configApplicationContext.getBean("commentBean", Comment.class);
        entityManager.persist(product);
        Optional<Product> regProd = productRepository.findByName("Blue band");
        Long productId = regProd.get().getId();
        review.setProductId(productId);
        review.setProduct(regProd.get());
        entityManager.persist(review);
        Optional<Review> review1 = reviewsRepository.findByProductId(productId);
        Long reviewId = review1.get().getId();
        this.reviewId = reviewId;
        this.comment = comment;

    }

    @Test
    public void save(){
        persistComment();
        Optional<Comment> comment1 = commentRepository.findByReviewId(reviewId);
        assertThat(comment1.get().getTitle()).isEqualTo(comment.getTitle());
    }

    @Test
    public void findProductReviews(){
        persistComment();
        Optional<List<Comment>> comment1 = commentRepository.findAllByReviewId(reviewId);
        assertThat(comment1.get().size()).isEqualTo(1);
    }

    private void persistComment(){

        comment.setReviewId(reviewId);
        comment.setReview(review);
        entityManager.persist(comment);
    }
}