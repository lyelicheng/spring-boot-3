package com.llye.springboot.springbootthree.controller;

import io.micrometer.observation.annotation.Observed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Observed(name = "homeController")
    @GetMapping("/")
    public String home() throws InterruptedException {
        return "Spring Boot Three API is up!";
    }
}
