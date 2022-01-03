package com.simple.restapi.controllers.views;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserViewController {
    @Async
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Simple Rest API Login");
        model.addAttribute("loginTitle", "Simple Rest API Login");
        return "login";
    }
}
