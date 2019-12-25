package com.spring01.reviews.repository;
import com.spring01.reviews.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    void deleteById(Long aLong);
    Optional<Product> findByName(String name);
    Optional<Product> findByProductcode(String productCode);
    List<Product> findAllProductsOrderedById(Integer limit, Integer offset);


}