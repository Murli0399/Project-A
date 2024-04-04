package com.murli.repository;

import com.murli.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Integer> {
    List<Product> findByName(String name);
    List<Product> findByPriceGreaterThanAndQuantityNot(int price, int quantity);
}
