package com.simple.restapi.services;

import com.simple.restapi.model.dao.UserDao;
import com.simple.restapi.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return userDao.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public Iterable<?> findByEmailContains(String email, Pageable pageable) {
        return userDao.findByEmailContains(email, pageable);
    }

    public Iterable<?> findAll() {
        return userDao.findAll();
    }
}
