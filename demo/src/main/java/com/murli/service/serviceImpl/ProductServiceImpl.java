package com.murli.service.serviceImpl;

import com.murli.dto.ProductDTO;
import com.murli.model.Product;
import com.murli.repository.ProductRepository;
import com.murli.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
         return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer id, ProductDTO product) {
        Optional<Product> oldProduct = productRepository.findById(id);
        if(!oldProduct.isPresent())
            throw new RuntimeException("Product Not Present");

        Product old = oldProduct.get();
        old.setName(product.getName());
        old.setPrice(product.getPrice());
        old.setDescription(product.getDescription());
        old.setQuantity(product.getQuantity());
        return productRepository.save(old);
    }

    @Override
    public void deleteProduct(Integer id) {
        Optional<Product> oldProduct = productRepository.findById(id);
        if(!oldProduct.isPresent())
            throw new RuntimeException("Product Not Present");

        productRepository.deleteById(id);
    }

    @Override
    public List<Product> viewAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findProductsByPriceAndQuantityCriteria() {
        // Specify the price and quantity criteria as needed
        int minPrice = 5000;
        int quantityNotEqual = 0;
        return productRepository.findByPriceGreaterThanAndQuantityNot(minPrice, quantityNotEqual);
    }

}
