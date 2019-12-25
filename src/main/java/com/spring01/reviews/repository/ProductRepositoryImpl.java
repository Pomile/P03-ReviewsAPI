package com.spring01.reviews.repository;

import com.spring01.reviews.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> findAllProductsOrderedById(Integer limit, Integer offset){
        return entityManager
                .createQuery("SELECT p from Product p ORDER BY p.id", Product.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

}