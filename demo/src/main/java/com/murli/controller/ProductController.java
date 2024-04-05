package com.murli.controller;

import com.murli.dto.ProductDTO;
import com.murli.model.Product;
import com.murli.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String hello(){
        return "Hello";
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product saveProduct = productService.addProduct(product);
        return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO product){
        Product saveProduct = productService.updateProduct(id,product);
        return new ResponseEntity<>(saveProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product Deleted Successful",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Product>> viewAll(){
        List<Product> products = productService.viewAll();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }



}
