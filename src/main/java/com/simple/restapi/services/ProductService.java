package com.simple.restapi.services;

import com.simple.restapi.model.dao.ProductDao;
import com.simple.restapi.model.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public Product findById(Long id) {
        return productDao.findById(id).get();
    }

    public Product save(Product product) {
        return productDao.save(product);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void removeById(Long id) {
       productDao.deleteById(id);
    }

    public List<Product> findByNameContains(String name){
        return productDao.findByNameContains(name);
    }
}
