package com.simple.restapi.services;

import com.simple.restapi.model.dao.CategoryDao;
import com.simple.restapi.model.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public Category save(Category category) {
        return categoryDao.save(category);
    }

    public Category findById(Long id) {
        return categoryDao.findById(id).get();
    }

    public void removeById(Long id) {
        categoryDao.deleteById(id);
    }

    public Iterable<Category> findAll() {
        return categoryDao.findAll();
    }
}