package com.simple.restapi.controllers;

import com.simple.restapi.dto.Messages;
import com.simple.restapi.dto.ResponseMessage;
import com.simple.restapi.dto.entities.CategoryDto;
import com.simple.restapi.model.entities.Category;
import com.simple.restapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

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

        if (errors.hasErrors()){
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
        } catch (NoSuchElementException e){
            messages.getMessages().add("Data with ID: " +  id + " not found");
            return new ResponseEntity<>(messages, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>("something_wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(category);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable("id") Long id){
        Category category;

        try {
            categoryService.removeById(id);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>("data_not_found", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>("something_wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("success");
    }
}
