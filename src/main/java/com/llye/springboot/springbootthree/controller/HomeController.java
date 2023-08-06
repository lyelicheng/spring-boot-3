package com.llye.springboot.springbootthree.controller;

import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @Observed(name = "homeController")
    @GetMapping("/")
    public String home() {
        LOG.info("Verify if micrometer tracing is functioning correctly.");
        return "Spring Boot Three API is up!";
    }
}
