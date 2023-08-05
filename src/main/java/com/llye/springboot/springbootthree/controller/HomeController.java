package com.llye.springboot.springbootthree.controller;

import io.micrometer.observation.annotation.Observed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class HomeController {
    private static final Logger LOG = Logger.getLogger(HomeController.class.getName());


    @Observed(name = "homeController")
    @GetMapping("/")
    public String home() throws InterruptedException {
        Thread.sleep(2000);
        return "Spring Boot Three API is up!";
    }
}
