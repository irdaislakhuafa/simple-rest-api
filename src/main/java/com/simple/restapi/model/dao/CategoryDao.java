package com.simple.restapi.model.dao;

import com.simple.restapi.model.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDao extends CrudRepository<Category, Long> {
    Page<Category> findByNameContains(String name, Pageable pageable);
}
