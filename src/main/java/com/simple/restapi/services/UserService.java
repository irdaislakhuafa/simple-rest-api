package com.simple.restapi.services;

import java.util.Optional;

import com.simple.restapi.model.dao.UserDao;
import com.simple.restapi.model.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.findByEmail(s).get();
    }

    @Async
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return userDao.save(user);
    }

    @Async
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Async
    public Iterable<?> findByEmailContains(String email, Pageable pageable) {
        return userDao.findByEmailContains(email, pageable);
    }

    @Async
    public Iterable<?> findAll() {
        return userDao.findAll();
    }

    @Async
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Async
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }
}
