package com.simple.restapi.services;

import com.simple.restapi.model.dao.SupplierDao;
import com.simple.restapi.model.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierDao supplierDao;

    public Supplier save(Supplier supplier) {
        return supplierDao.save(supplier);
    }

    public Supplier findById(Long id) {
        return supplierDao.findById(id).get();
    }

    public void removeById(Long id) {
        supplierDao.deleteById(id);
    }

    public Iterable<Supplier> findAll() {
        return supplierDao.findAll();
    }

    public List<Supplier> findByName(String name, String sortType) {
        if (sortType.contains("desc")) {
            return supplierDao.findByNameContainsOrderByIdDesc(name);
        } else {
            return supplierDao.findByNameContainsOrderByIdAsc(name);
        }
    }

    public Supplier findByEmail(String email) {
        return supplierDao.findByEmail(email);
    }

    public List<?> findByNameStartsWith(String name) {
        return supplierDao.findByNameStartingWith(name);
    }
    public List<?> findByNameOrEmail(String name, String email){
        return supplierDao.findByNameContainsOrEmailContains(name, email);
    }
}
