package com.simple.restapi.controllers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restapi/test")
public class TestController {
    @Async
    @GetMapping
    public String test() {
        return "Test Simple Rest API";
    }
}
