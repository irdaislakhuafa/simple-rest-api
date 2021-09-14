package com.simple.restapi.model.dao;

import com.simple.restapi.model.entities.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface SupplierDao extends CrudRepository<Supplier, Long> {
}
