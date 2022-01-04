package com.simple.restapi.helpers;

import com.simple.restapi.model.entities.User;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserInfo {
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
