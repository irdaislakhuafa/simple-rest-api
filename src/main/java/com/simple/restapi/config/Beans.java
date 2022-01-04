package com.simple.restapi.config;

import com.simple.restapi.helpers.UserInfo;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
    @Bean
    public ModelMapper modelmapper() {
        return new ModelMapper();
    }

    @Bean
    public UserInfo userInfo() {
        return new UserInfo();
    }
}
