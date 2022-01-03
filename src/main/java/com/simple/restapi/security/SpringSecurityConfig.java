package com.simple.restapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DaoAuthenticationProvider provider;

    @Override // allow all people access /restapi/users/register without security
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        // disable security in register
                        "/restapi/users/register",
                        // disable security in /webjars/** to enable CSS/JS/Bootstrap
                        "/webjars/**", "/style.css",
                        // enable /images
                        "/images/**")
                .permitAll()
                .anyRequest().fullyAuthenticated()
                .and().formLogin().loginPage("/user/login").permitAll()
        // .and().httpBasic();
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.authenticationProvider(provider);
    }
}
