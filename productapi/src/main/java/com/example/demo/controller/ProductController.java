package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

import com.example.demo.model.Product;

@RestController
@RequestMapping("/products")

public class ProductController {

    List<Product> products = new ArrayList<>();

    // TASK 6.1 - Add Product (POST)
    @PostMapping
    public Product addProduct(@RequestBody Product product){
        products.add(product);
        return product;
    }

    // TASK 6.1 - Get All Products (GET)
    @GetMapping
    public List<Product> getAllProducts(){
        return products;
    }

    // TASK 6.2 - Get Product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id){

        for(Product p : products){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    // TASK 6.3 - Update Product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id,
                                 @RequestBody Product product){

        for(Product p : products){
            if(p.getId() == id){
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                return p;
            }
        }
        return null;
    }

    // TASK 6.3 - Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id){

        products.removeIf(p -> p.getId() == id);
        return "Product Deleted Successfully";
    }

    // TASK 6.4 - RequestParam Example
    @GetMapping("/search")
    public String searchProduct(@RequestParam String name){

        return "Searching for product: " + name;
    }

    // TASK 6.4 - ResponseEntity Example
    @GetMapping("/status")
    public ResponseEntity<String> checkStatus(){

        return ResponseEntity.ok("API is working successfully");
    }

}