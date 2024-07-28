package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<Iterable<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProduct(),HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
    }

    @GetMapping("/productsByCategory/{category}")
    public ResponseEntity<Iterable<Product>> getProductByCategory(@PathVariable String category) {
        return new ResponseEntity<>(productService.searchProductsByCategory(category),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{Id}")
    public void deleteProduct(@PathVariable String Id){
        productService.deleteById(Id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        productService.deleteAll();
    }

    @PutMapping("/update/{Id}")
    public ResponseEntity<Product> update(@PathVariable String Id, @RequestBody Product product){
        return new ResponseEntity<>(productService.updateProduct(Id, product),HttpStatus.ACCEPTED);
    }

}