package com.simple.restapi.services;

import com.simple.restapi.helpers.Messages;
import com.simple.restapi.model.dao.ProductDao;
import com.simple.restapi.model.entities.Category;
import com.simple.restapi.model.entities.Product;
import com.simple.restapi.model.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    public Product findById(Long id) {
        return productDao.findById(id).get();
    }

    public Product save(Product product) {
        return productDao.save(product);
    }

    public Iterable<Product> findAll() {
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

    public ResponseEntity<?> findProductsByName(String name) {
        List<Product> products = productDao.findProductsByName(name);

        if (products.size() == 0) {
            return new Messages().nameNotFound("Product", name);
        } else {
            return ResponseEntity.ok(products);
        }
    }

    public ResponseEntity<?> findProductsByNameLike(String name) {
        List<Product> products = productDao.findProductsByNameLike("%" + name + "%");

        if (products.size() <= 0) {
            return new Messages().nameNotFound("Product", name);
        } else {
            return ResponseEntity.ok(products);
        }
    }

    public ResponseEntity<?> findProductsByCategory(Long categoryId) throws Exception {
        Category category = categoryService.findById(categoryId);

        if (category == null) {
            return new Messages().idNotFound(categoryId);
        } else {
            return ResponseEntity.ok(productDao.findProductsByCategoryId(categoryId));
        }
    }

    public ResponseEntity<?> findProductsBySupplier(Long supplierId) {
        Supplier supplier = supplierService.findById(supplierId);

        if (supplier == null) {
            return new Messages().idNotFound("Supplier", supplierId);
        }
        List<Product> products = productDao.findProductsBySupplier(supplier);
        return ResponseEntity.ok(products);
    }

    public void setCategory(Category category, Long productId) throws Exception{
        Product product = findById(productId);
        product.setCategory(category);
        save(product);
    }
}
