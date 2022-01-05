package com.simple.restapi.controllers.views;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.simple.restapi.dto.entities.UserDto;
import com.simple.restapi.model.entities.User;
import com.simple.restapi.services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserViewController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HttpServletResponse response;

    @Async
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Simple Rest API Login");
        model.addAttribute("loginTitle", "Simple Rest API Login");
        return "login";
    }

    @Async
    @GetMapping("/logout")
    public String logout() {
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/user/login";
    }

    @Async
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Simple Rest API Register");
        model.addAttribute("userDto", new UserDto() {
            {
                setAccessLevel("user".toUpperCase());
            }
        });
        return "register";
    }

    @Async
    @PostMapping("/register")
    public String register(Model model, UserDto userDto) {
        if (userDto != null) {
            try {
                new Thread(() -> {
                    User user = modelMapper.map(userDto, User.class);
                    userService.register(user);
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/user/login";
    }
}
