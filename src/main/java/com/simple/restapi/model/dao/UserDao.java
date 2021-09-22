package com.simple.restapi.model.dao;

import com.simple.restapi.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Page<User> findByEmailContains(String email, Pageable pageable);
}
