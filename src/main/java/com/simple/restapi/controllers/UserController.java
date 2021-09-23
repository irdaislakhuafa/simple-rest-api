package com.simple.restapi.controllers;

import com.simple.restapi.dto.ResponseMessage;
import com.simple.restapi.dto.entities.UserDto;
import com.simple.restapi.helpers.Search;
import com.simple.restapi.helpers.SearchOnly;
import com.simple.restapi.model.entities.User;
import com.simple.restapi.model.entities.utils.AccessLevel;
import com.simple.restapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/restapi/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

//    @GetMapping
//    public ResponseEntity<?> findAll() {
//        return ResponseEntity.ok(userService.findAll());
//    }
    @GetMapping
    public Iterable<?> findAll() {
        return userService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto, Errors errors) {
        ResponseMessage<User> response = new ResponseMessage<>();
        userDto.setAccessLevel(userDto.getAccessLevel().toUpperCase());
        User user = modelMapper.map(userDto, User.class);

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            boolean isUserExists = userService.findByEmail(user.getEmail().toLowerCase()).isPresent();

            if (isUserExists) {
                response.setStatus(false);
                response.getMessages().add(
                        "User with email " + user.getEmail() + " already exists!"
                );
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.setStatus(true);
                response.getMessages().add("Saved success");
                response.setData(userService.register(user));
                return ResponseEntity.ok(response);
            }
        }
    }

    @PostMapping("/search/email")
    public ResponseEntity<?> findByEmail(@RequestBody SearchOnly searchOnly) {
        Optional<User> optionalUser = userService.findByEmail(searchOnly.getKeyword());

        if (optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/search/email_contains")
    public ResponseEntity<?> findByEmailContains(@RequestBody Search search){
        Pageable pageable;

        if (search.getSort().getSortOrder().contains("asc")){
            pageable = PageRequest.of(
                    search.getRequestData().getPage(),
                    search.getRequestData().getSize(),
                    Sort.by(search.getSort().getSortBy()).ascending()
            );
        } else {
            pageable = PageRequest.of(
                    search.getRequestData().getPage(),
                    search.getRequestData().getSize(),
                    Sort.by(search.getSort().getSortBy()).descending()
            );
        }
        Iterable<?> dataUsers = userService.findByEmailContains(search.getKeyword(), pageable);
        return ResponseEntity.ok(dataUsers);
    }
}
