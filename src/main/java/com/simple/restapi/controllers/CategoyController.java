package com.simple.restapi.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.simple.restapi.dto.ResponseMessage;
import com.simple.restapi.dto.entities.CategoryDto;
import com.simple.restapi.dto.entities.CategoryDtoFull;
import com.simple.restapi.helpers.Messages;
import com.simple.restapi.helpers.Search;
import com.simple.restapi.model.entities.Category;
import com.simple.restapi.services.CategoryService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restapi/categories")
public class CategoyController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CategoryDto categoryDto, Errors errors) {
        ResponseMessage<Category> response = new ResponseMessage<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            Category category = modelMapper.map(categoryDto, Category.class);

            response.setStatus(true);
            response.setData(categoryService.save(category));
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping
    public Iterable<?> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Category category;
        Messages messages = new Messages();

        try {
            category = categoryService.findById(id);
        } catch (NoSuchElementException e) {
            return messages.idNotFound(id);
        } catch (Exception e) {
            return messages.uknownError();
        }
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable("id") Long id) {
        Messages messages = new Messages();

        try {
            categoryService.removeById(id);
        } catch (NoSuchElementException e) {
            return messages.idNotFound(id);
        } catch (Exception e) {
            return messages.uknownError();
        }
        return ResponseEntity.ok("success");
    }

    @PostMapping("/search/with_paging")
    public ResponseEntity<?> findByNameContains(@RequestBody Search search) {
        Pageable pageable;

        if (search.getSort().getSortOrder().contains("desc")) {
            pageable = PageRequest.of(
                    search.getRequestData().getPage(),
                    search.getRequestData().getSize(),
                    Sort.by(search.getSort().getSortBy()).descending());
        } else {
            pageable = PageRequest.of(
                    search.getRequestData().getPage(),
                    search.getRequestData().getSize(),
                    Sort.by(search.getSort().getSortBy()).ascending());
        }
        return ResponseEntity.ok(categoryService.findByNameContains(search.getKeyword(), pageable));
    }

    @PostMapping("/saveall")
    public ResponseEntity<?> saveAll(@RequestBody List<Category> categoryList) {
        ResponseMessage<Iterable<Category>> response = new ResponseMessage<>();
        response.setStatus(true);
        response.setData(categoryService.saveAll(categoryList));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody CategoryDtoFull categoryDtoFull, Errors errors) {
        ResponseMessage<Category> response = new ResponseMessage<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            Category category = modelMapper.map(categoryDtoFull, Category.class);
            Category tempCategory = null;
            try {
                tempCategory = categoryService.findById(categoryDtoFull.getId());
            } catch (NoSuchElementException e) {
                return new Messages().idNotFound(categoryDtoFull.getId(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (tempCategory != null) {
                category.setCreatedBy(tempCategory.getCreatedBy());
                category.setCreatedDate(tempCategory.getCreatedDate());
            }
            response.setStatus(true);
            response.setData(categoryService.save(category));
            return ResponseEntity.ok(response);
        }
    }
}
