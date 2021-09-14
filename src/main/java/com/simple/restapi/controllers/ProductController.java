package com.simple.restapi.controllers;

import com.simple.restapi.dto.ResponseMessage;
import com.simple.restapi.model.entities.Product;
import com.simple.restapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/restapi/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Product product, Errors errors) {
        ResponseMessage<Product> response = new ResponseMessage<>();

        if (errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()){
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response.setStatus(true);
            response.setData(productService.save(product));
            return ResponseEntity.ok(response);
        }
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
    public ResponseEntity<?> update(@Valid @RequestBody Product product, Errors errors) {
        ResponseMessage<Product> response = new ResponseMessage<>();

        if (product.getId() == null) {
            response.getMessages().add("Id is required for update");
        }
        if (errors.hasErrors() || response.getMessages().size() != 0){
            for (ObjectError error : errors.getAllErrors()){
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response.setStatus(true);
            response.setData(productService.save(product));
            return ResponseEntity.ok(response);
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
