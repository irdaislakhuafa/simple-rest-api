package com.simple.restapi.services;

import java.util.List;

import com.simple.restapi.model.dao.SupplierDao;
import com.simple.restapi.model.entities.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    @Autowired
    private SupplierDao supplierDao;

    @Async
    public Supplier save(Supplier supplier) {
        return supplierDao.save(supplier);
    }

    @Async
    public Supplier findById(Long id) {
        return supplierDao.findById(id).get();
    }

    @Async
    public void removeById(Long id) {
        supplierDao.deleteById(id);
    }

    @Async
    public Iterable<Supplier> findAll() {
        return supplierDao.findAll();
    }

    @Async
    public List<Supplier> findByName(String name, String sortType) {
        if (sortType.contains("desc")) {
            return supplierDao.findByNameContainsOrderByIdDesc(name);
        } else {
            return supplierDao.findByNameContainsOrderByIdAsc(name);
        }
    }

    @Async
    public Supplier findByEmail(String email) {
        return supplierDao.findByEmail(email);
    }

    @Async
    public List<?> findByNameStartsWith(String name) {
        return supplierDao.findByNameStartingWith(name);
    }

    @Async
    public List<?> findByNameOrEmail(String name, String email) {
        return supplierDao.findByNameContainsOrEmailContains(name, email);
    }
}
