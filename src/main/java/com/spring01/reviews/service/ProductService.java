package com.spring01.reviews.service;

import com.spring01.reviews.exception.ProductNotFoundException;
import com.spring01.reviews.model.Product;
import com.spring01.reviews.repository.ProductRepository;
import com.spring01.reviews.repository.ProductRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Implements the product service create, read, update or delete
 * information about products.
 */
@Service
public class ProductService{
    private ProductRepository productRepository;
    private ProductRepositoryImpl productRepositoryImpl;

    public ProductService(ProductRepository productRepository, ProductRepositoryImpl productRepositoryImpl) {
        this.productRepository = productRepository;
        this.productRepositoryImpl = productRepositoryImpl;
    }

    /**
    ** creates a Product
    * @param product A product object, which should be new
    * @return the new product is stored in the repository
    */
    public Product save(Product product){
        return productRepository.save(product);
    }

    public Product update(Product product){
        return productRepository.save(product);
    }

    /**
     ** find a Product by Id
     * @param id an integer
     * @return a product
     */
    public Optional<Product> findById(Integer id){
        return productRepository.findById((Long.valueOf(id)));
    }

    /**
     ** find a Product by productCode
     * @param code a String
     * @return a product
     */
    public Optional<Product> findByProductCode(String code){
        return productRepository.findByProductcode(code);
    }

    /**
     ** find all products with limit starting from offset position
     * @param limit an integer
     * @param offset an integer
     * @return a list of products
     */
    public List<Product> findAllProducts(Integer limit, Integer offset){
        return productRepositoryImpl.findAllProductsOrderedById(limit, offset);
    }
}