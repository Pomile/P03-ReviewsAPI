package com.spring01.reviews.repository;

import org.springframework.data.repository.CrudRepository;
import com.spring01.reviews.model.Product;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByName(String name);

    @Override
    Optional<Product> findById(Long aLong);
}