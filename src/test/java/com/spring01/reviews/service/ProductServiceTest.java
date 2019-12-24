package com.spring01.reviews.service;

import com.spring01.reviews.model.Product;
import com.spring01.reviews.repository.ProductRepository;
import com.spring01.reviews.repository.ProductRepositoryImpl;
import org.junit.Before;
import org.mockito.internal.matchers.Null;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProductService.class})
public class ProductServiceTest {

    @MockBean private ProductRepository productRepository;
    @MockBean private ProductRepositoryImpl productRepositoryImpl;
    @Autowired private ProductService productService;

    @Before
    public void setup(){ }
    @Test
    public void save() {
        Product product = product();
        product.setId(1L);
        Mockito.when(
                productRepository.save(any()))
                .thenReturn(product);

        Optional<Product> product1 = Optional.ofNullable(productService.save(product));
        assertEquals("Blue band", product1.get().getName());
        assertEquals(1L, product1.get().getId());
    }
    @Test
    public void findAllProducts() {
        Product product = product();
        product.setId(1L);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(
                productRepositoryImpl.findAllProductsOrderedById(2, 0))
                .thenReturn(products);

        List<Product> prods = productService.findAllProducts(2, 0);
        assertEquals(1, prods.size());
    }
    @Test
    public void updateProduct() {
        Product product = product();
        product.setId(1L);
        product.setPrice(400.0);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(
                productRepository.save(product))
                .thenReturn(product);

        Product prods = productService.update(product);
        assertEquals(400.0, prods.getPrice());
    }

    @Test
    public void removeProduct() {

        Void prods = productService.removeById(1);
        assertEquals(null, prods);
    }

    private Product product(){
        Product product = new Product();
        product.setName("Blue band");
        product.setDescription("Nutritious Margarine");
        product.setPrice(700.00);
        product.setStock(20);
        product.setImage("blueBand.jpg");
        product.setProductCode("44344AB");

        return  product;
    }
}