package com.simple.restapi.model.dao;

import java.util.Optional;

import com.simple.restapi.model.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Page<User> findByEmailContains(String email, Pageable pageable);

    boolean existsByEmail(String email);
}
