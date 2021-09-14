package com.simple.restapi.services;

import com.simple.restapi.model.dao.ProductDao;
import com.simple.restapi.model.entities.Product;
import com.simple.restapi.model.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Product> findByNameContains(String name) {
        return productDao.findByNameContains(name);
    }

    public void addSupplier(Supplier supplier, Long productId) {
        Product product = findById(productId);
        product.getSuppliers().add(supplier);
        save(product);
    }
}
