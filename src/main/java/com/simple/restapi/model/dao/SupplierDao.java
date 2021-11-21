package com.simple.restapi.model.dao;

import com.simple.restapi.model.entities.Supplier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierDao extends CrudRepository<Supplier, Long> {
    List<Supplier> findByNameContainsOrderByIdDesc(String name);

    List<Supplier> findByNameContainsOrderByIdAsc(String name);

    Supplier findByEmail(String email);

    List<Supplier> findByNameStartingWith(String name);

    List<Supplier> findByNameContainsOrEmailContains(String name, String email);
}
