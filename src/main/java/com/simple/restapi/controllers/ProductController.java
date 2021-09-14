package com.simple.restapi.controllers;

import com.simple.restapi.model.entities.Product;
import com.simple.restapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/restapi/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Product product;
        try {
            product = productService.findById(id);
        } catch (NoSuchElementException e){
            product = null;
        }

        if (product == null) {
            return new ResponseEntity<>("data_not_found", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(product);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Product product) {
        if (product.getId() == null) {
            return new ResponseEntity<>("Id_is_required_for_update", HttpStatus.BAD_REQUEST);
        } else {
            save(product);
            return ResponseEntity.ok(product);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable("id") Long id) {
        Product product;
        try {
            product = productService.findById(id);
        } catch (NoSuchElementException e){
            product = null;
        }
        if (product == null) {
            return new ResponseEntity<>("data_not_found", HttpStatus.NOT_FOUND);
        } else {
            productService.removeById(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }
}
