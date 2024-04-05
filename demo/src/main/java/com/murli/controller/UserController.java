package com.murli.controller;

import com.murli.model.Product;
import com.murli.model.User;
import com.murli.service.ProductService;
import com.murli.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService uservices;

    @Autowired
    private ProductService productService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<User> saveUserController(@RequestBody User user) throws ExecutionControl.UserException {
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        User saveuser = uservices.register(user);

        return new ResponseEntity<User>(saveuser, HttpStatus.CREATED);
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Product>> viewAll(){
        List<Product> products = productService.viewAll();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/searchByName/{productName}")
    public ResponseEntity<List<Product>> searchByName(@PathVariable String productName){
        return new ResponseEntity<>(productService.searchProductsByName(productName),HttpStatus.OK);
    }

    @GetMapping("/findByPrice")
    public ResponseEntity<List<Product>> findByPrice(){
        return new ResponseEntity<>(productService.findProductsByPriceAndQuantityCriteria(),HttpStatus.OK);
    }
}
