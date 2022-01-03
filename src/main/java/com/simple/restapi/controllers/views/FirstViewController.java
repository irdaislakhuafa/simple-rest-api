package com.simple.restapi.controllers.views;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FirstViewController {
    @Async
    @GetMapping
    public String firstHandler() {
        return "redirect:/swagger-ui/#/";
    }
}
