package com.simple.restapi.controllers;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import com.simple.restapi.dto.ResponseMessage;
import com.simple.restapi.dto.entities.UserDto;
import com.simple.restapi.dto.entities.UserDtoFull;
import com.simple.restapi.helpers.Messages;
import com.simple.restapi.helpers.Search;
import com.simple.restapi.helpers.SearchOnly;
import com.simple.restapi.helpers.UserInfo;
import com.simple.restapi.model.entities.User;
import com.simple.restapi.services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restapi/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserInfo userInfo;

    @Async
    @GetMapping
    public ResponseEntity<?> findAll() {
        System.out.println(userInfo.getCurrentUser());
        return ResponseEntity.ok(userService.findAll());
    }

    @Async
    @GetMapping("/current")
    public ResponseEntity<?> currentUser() {
        return ResponseEntity.ok(userInfo.getCurrentUser());
    }

    @Async
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
                        "User with email " + user.getEmail() + " already exists!");
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

    @Async
    @PutMapping("/register/update")
    public ResponseEntity<?> updateUserProperties(@Valid @RequestBody UserDtoFull userDtoFull, Errors errors) {
        ResponseMessage<User> response = new ResponseMessage<>();
        userDtoFull.setAccessLevel(userDtoFull.getAccessLevel().toUpperCase());
        User user = modelMapper.map(userDtoFull, User.class);

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            User tempUser = null;
            try {
                tempUser = userService.findById(user.getId()).get();
            } catch (NoSuchElementException e) {
                return new Messages().idNotFound(user.getId(), response);
            } catch (Exception e) {
                e.printStackTrace();
                return new Messages().uknownError();
            }
            if (tempUser != null) {
                user.setPassword(userDtoFull.getPassword());
            }

            try {
                user = userService.register(user);
            } catch (DataIntegrityViolationException e) {
                response.getMessages().add("Other user with email " + userDtoFull.getEmail() + " already exists");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.setStatus(true);
            response.getMessages().add("Saved success!");
            response.setData(user);
            return ResponseEntity.ok(response);
        }
    }

    @Async
    @PostMapping("/search/email")
    public ResponseEntity<?> findByEmail(@RequestBody SearchOnly searchOnly) {
        try {
            Optional<User> optionalUser = userService.findByEmail(searchOnly.getKeyword());

            if (optionalUser.isPresent()) {
                return ResponseEntity.ok(optionalUser.get());
            } else {
                return new ResponseEntity<>(optionalUser.get(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Async
    @PostMapping("/search/email_contains")
    public ResponseEntity<?> findByEmailContains(@RequestBody Search search) {
        Pageable pageable;

        if (search.getSort().getSortOrder().contains("asc")) {
            pageable = PageRequest.of(
                    search.getRequestData().getPage(),
                    search.getRequestData().getSize(),
                    Sort.by(search.getSort().getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(
                    search.getRequestData().getPage(),
                    search.getRequestData().getSize(),
                    Sort.by(search.getSort().getSortBy()).descending());
        }
        Iterable<?> dataUsers = userService.findByEmailContains(search.getKeyword(), pageable);
        return ResponseEntity.ok(dataUsers);
    }
}
