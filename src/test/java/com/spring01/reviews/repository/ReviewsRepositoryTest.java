package com.spring01.reviews.repository;
import com.spring01.reviews.DAO.ReviewDao;
import com.spring01.reviews.config.DataConfig;
import com.spring01.reviews.model.Product;
import com.spring01.reviews.model.Review;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
import java.sql.SQLException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReviewsRepositoryTest {
    private SessionFactory sessionFactory;
    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TestEntityManager testEntityManager;
    @Autowired private ReviewsRepository reviewsRepository;
    @Autowired private ProductRepository productRepository;
    private AnnotationConfigApplicationContext configApplicationContext;
    private Long productId;
    private Optional<Product> prod;

    @Before
    public void init() throws SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        System.err.println("Connected to" + con.getMetaData().getURL());
        configApplicationContext = new AnnotationConfigApplicationContext(DataConfig.class);
        Product product = configApplicationContext.getBean("productBean", Product.class);
        entityManager.persist(product);
        Optional<Product> regProd = productRepository.findByName("Blue band");
        this.productId = regProd.get().getId();
        this.prod = regProd;
    }

    @Test
    public void save(){
        System.err.println("ID: "+ productId);
        Review review = configApplicationContext.getBean("reviewBean", Review.class);
        review.setProductId(productId);
        review.setProduct(this.prod.get());
        entityManager.persist(review);
        Optional<Review> review1 = reviewsRepository.findByProductId(productId);
        assertThat(review1.get().getTitle()).isEqualTo(review.getTitle());
    }

}