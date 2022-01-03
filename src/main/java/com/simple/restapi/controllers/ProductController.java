package com.simple.restapi.controllers;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.simple.restapi.dto.ResponseMessage;
import com.simple.restapi.dto.entities.ProductDto;
import com.simple.restapi.dto.entities.ProductDtoFull;
import com.simple.restapi.dto.entities.SupplierDtoFull;
import com.simple.restapi.helpers.Messages;
import com.simple.restapi.helpers.Search;
import com.simple.restapi.model.entities.Category;
import com.simple.restapi.model.entities.Product;
import com.simple.restapi.model.entities.Supplier;
import com.simple.restapi.services.ProductService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restapi/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Async
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ProductDto productDto, Errors errors) {
        ResponseMessage<Product> response = new ResponseMessage<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            Product product = modelMapper.map(productDto, Product.class);
            response.setStatus(true);
            response.setData(productService.save(product));
            return ResponseEntity.ok(response);
        }
    }

    @Async
    @GetMapping
    public Iterable<Product> findAll() {
        return productService.findAll();
    }

    @Async
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Product product;
        try {
            product = productService.findById(id);
        } catch (NoSuchElementException e) {
            return new Messages().idNotFound(id);
        } catch (Exception e) {
            return new Messages().uknownError();
        }
        return ResponseEntity.ok(product);
    }

    @Async
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ProductDtoFull productDtoFull, Errors errors) {
        ResponseMessage<Product> response = new ResponseMessage<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            Product product = modelMapper.map(productDtoFull, Product.class);
            Product tempProduct = null;
            try {
                tempProduct = productService.findById(productDtoFull.getId());
            } catch (NoSuchElementException e) {
                return new Messages().idNotFound(productDtoFull.getId(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (tempProduct != null) {
                product.setCreatedBy(tempProduct.getCreatedBy());
                product.setCreatedDate(tempProduct.getCreatedDate());
            }

            response.setStatus(true);
            response.setData(productService.save(product));
            return ResponseEntity.ok(response);
        }
    }

    @Async
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable("id") Long id) {
        Product product;
        try {
            product = productService.findById(id);
        } catch (NoSuchElementException e) {
            return new Messages().idNotFound(id);
        } catch (Exception e) {
            return new Messages().uknownError();
        }
        productService.removeById(id);
        return new Messages().success();
    }

    @Async
    @PutMapping("/{id}")
    public ResponseEntity<?> addSupplier(@Valid @RequestBody SupplierDtoFull supplierDtoFull, Errors errors,
            @PathVariable("id") Long productId) {
        ResponseMessage<Supplier> response = new ResponseMessage<>();
        Messages messages = new Messages();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            try {
                productService.findById(productId);
            } catch (NoSuchElementException e) {
                return messages.idNotFound(productId);
            } catch (Exception e) {
                return messages.uknownError();
            }
            Supplier supplier = modelMapper.map(supplierDtoFull, Supplier.class);
            productService.addSupplier(supplier, productId);
            response.setStatus(true);
            response.setData(supplier);
            return ResponseEntity.ok(response);
        }
    }

    @Async
    @PutMapping("/set/category/{productId}")
    public ResponseEntity<?> setCategory(@RequestBody Category category, @PathVariable Long productId) {
        Messages messages = new Messages();
        try {
            productService.setCategory(category, productId);
        } catch (NoSuchElementException e) {
            return messages.idNotFound("Product", productId);
        } catch (DataIntegrityViolationException e) {
            return messages.idNotFound("Category", category.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return messages.uknownError();
        }
        messages.getMessages().add("success");
        return ResponseEntity.ok(messages);
    }

    @Async
    @PostMapping("/search/name")
    public ResponseEntity<?> findByName(@RequestBody Search search) {
        return productService.findProductsByName(search.getKeyword());
    }

    @Async
    @PostMapping("/search/name/like")
    public ResponseEntity<?> findByNameLike(@RequestBody Search search) {
        return productService.findProductsByNameLike(search.getKeyword());
    }

    @Async
    @GetMapping("/search/category/{id}")
    public ResponseEntity<?> findByCategoryId(@PathVariable("id") Long id) {
        ResponseEntity<?> response;

        try {
            response = productService.findProductsByCategory(id);
        } catch (NoSuchElementException e) {
            return new Messages().idNotFound(id);
        } catch (Exception e) {
            return new Messages().uknownError();
        }
        return response;
    }

    @Async
    @GetMapping("/search/supplier/{id}")
    public ResponseEntity<?> findBySupplier(@PathVariable("id") Long id) {
        ResponseEntity<?> response = productService.findProductsBySupplier(id);
        try {
            response = productService.findProductsBySupplier(id);
        } catch (NoSuchElementException e) {
            return new Messages().idNotFound("Supplier", id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Messages().uknownError();
        }
        return response;
    }

}
