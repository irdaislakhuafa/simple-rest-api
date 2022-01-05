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
        // start config
        http
                // .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        // disable security in register
                        "/restapi/users/register",
                        // disable security in /webjars/** to enable CSS/JS/Bootstrap
                        "/webjars/**", "/style.css",
                        // enable /images
                        "/images/**",
                        // disable security in register page
                        "/user/register")
                .permitAll()

                .antMatchers(
                        "/restapi/users",
                        "/restapi/users/register/**")
                .hasAnyAuthority("ADMIN")

                .anyRequest().fullyAuthenticated()
                .and().formLogin().loginPage("/user/login").permitAll()
        // .and()
        // .logout().logoutUrl("/user/logout").permitAll().logoutSuccessUrl("/user/login")
        // .invalidateHttpSession(true)
        // .deleteCookies("JSESSIONID")
        // .permitAll()

        // .logoutUrl("/logout").logoutSuccessUrl("/user/login").deleteCookies("JSESSIONID").permitAll()
        // end config;
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.authenticationProvider(provider);
    }
}
