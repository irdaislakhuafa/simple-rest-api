package com.simple.restapi.services;

import com.simple.restapi.model.dao.CategoryDao;
import com.simple.restapi.model.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Async
    public Category save(Category category) {
        return categoryDao.save(category);
    }

    @Async
    public Category findById(Long id) {
        return categoryDao.findById(id).get();
    }

    @Async
    public void removeById(Long id) {
        categoryDao.deleteById(id);
    }

    @Async
    public Iterable<Category> findAll() {
        return categoryDao.findAll();
    }

    @Async
    public Iterable<Category> findByNameContains(String name, Pageable pageable) {
        return categoryDao.findByNameContains(name, pageable);
    }

    @Async
    public Iterable<Category> saveAll(List<Category> categories) {
        return categoryDao.saveAll(categories);
    }
}
