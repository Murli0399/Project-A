package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Add new Product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // For getting all product
    public Iterable<Product> getAllProduct() {
        return productRepository.findAll();
    }

    // Product by id
    public Product getProductById(String id) {

        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
            throw new RuntimeException("Product not found");

        return product.get();
    }

    // Product by category
    public List<Product> searchProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // Delete product by id
    public void deleteById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
            throw new RuntimeException("Product not found");
        productRepository.deleteById(id);
    }

    // Delete all products
    public void deleteAll() {
        productRepository.deleteAll();
    }

    // Update product details
    public Product updateProduct(String id,Product product) {
        Optional<Product> op = productRepository.findById(id);
        if (op.isEmpty())
            throw new RuntimeException("Product not found");

        Product p = op.get();
        System.out.println(p);
        p.setName(product.getName() == null ? p.getName() : product.getName());
        p.setPrice(product.getPrice() == 0 ? p.getPrice() : product.getPrice());
        p.setCategory(product.getCategory() == null ? p.getCategory() : product.getCategory());

        return productRepository.save(p);
    }


}
