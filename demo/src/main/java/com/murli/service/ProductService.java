package com.murli.service;

import com.murli.dto.ProductDTO;
import com.murli.model.Product;

import java.util.List;

public interface ProductService {
    public Product addProduct(Product product);
    public Product updateProduct(Integer id, ProductDTO product);
    public void deleteProduct(Integer id);
    public List<Product> viewAll();
    public List<Product> searchProductsByName(String name);
    public List<Product> findProductsByPriceAndQuantityCriteria();
}
