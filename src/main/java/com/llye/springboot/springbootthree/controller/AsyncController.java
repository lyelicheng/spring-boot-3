package com.llye.springboot.springbootthree.controller;

import com.llye.springboot.springbootthree.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
public class AsyncController {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncController.class);

    private final AsyncService asyncService;

    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping
    public ResponseEntity<String> executeAsync() {
        LOG.info("API Request before Async method call.");
        asyncService.execute();
        LOG.info("API Request after Async method call.");
        return new ResponseEntity<>("Request accepted", HttpStatus.OK);
    }
}
