package com.simple.restapi.controllers;

import com.simple.restapi.dto.ResponseMessage;
import com.simple.restapi.dto.entities.SupplierDto;
import com.simple.restapi.dto.entities.SupplierDtoFull;
import com.simple.restapi.helpers.Messages;
import com.simple.restapi.helpers.Search;
import com.simple.restapi.model.entities.Supplier;
import com.simple.restapi.services.SupplierService;
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
@RequestMapping("/restapi/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Iterable<Supplier> findAll() {
        return supplierService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody SupplierDto supplierDto, Errors errors) {
        ResponseMessage<Supplier> response = new ResponseMessage<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            Supplier supplier = modelMapper.map(supplierDto, Supplier.class);

            response.setStatus(true);
            response.setData(supplierService.save(supplier));
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Supplier supplier;
        Messages messages = new Messages();
        try {
            supplier = supplierService.findById(id);
        } catch (NoSuchElementException e) {
            supplier = null;
            messages.getMessages().add("Data with ID: " + id + " not found");
        }

        if (supplier == null) {
            return new ResponseEntity<>(messages, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(supplier);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody SupplierDtoFull supplierDtoFull, Errors errors) {
        ResponseMessage<Supplier> response = new ResponseMessage<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            Supplier supplier = modelMapper.map(supplierDtoFull, Supplier.class);
            Supplier tempSupplier = null;
            try {
                tempSupplier = supplierService.findById(supplierDtoFull.getId());
            } catch (NoSuchElementException e){
                return new Messages().idNotFound(supplierDtoFull.getId(), response);
            } catch (Exception e){
                e.printStackTrace();
            }
            if (tempSupplier != null){
                supplier.setCreatedBy(tempSupplier.getCreatedBy());
                supplier.setCreatedDate(tempSupplier.getCreatedDate());
            }
            response.setStatus(true);
            response.setData(supplierService.save(supplier));
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable("id") Long id) {
        Messages messages = new Messages();

        try {
            supplierService.removeById(id);
        } catch (NoSuchElementException e) {
            messages.getMessages().add("Data with ID: " + id + " not found");
            return new ResponseEntity<>(messages, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }

    @PostMapping("/search/name_contains")
    public ResponseEntity<?> findByName(@RequestBody Search search) {
        return ResponseEntity.ok(supplierService.findByName(search.getKeyword(), search.getSort().getSortOrder()));
    }

    @PostMapping("/search/email")
    public ResponseEntity<?> findByEmail(@RequestBody Search search) {
        return ResponseEntity.ok(supplierService.findByEmail(search.getKeyword()));
    }
    @PostMapping("/search/name_starting")
    public ResponseEntity<?> findByNameStartsWith(@RequestBody Search search) {
        return ResponseEntity.ok(supplierService.findByNameStartsWith(search.getKeyword()));
    }
    @PostMapping("/search/name_or_email")
    public ResponseEntity<?> findByNameOrEmail(@RequestBody Search search) {
        return ResponseEntity.ok(supplierService.findByNameOrEmail(search.getKeyword(), search.getSecondKeyword()));
    }
}
