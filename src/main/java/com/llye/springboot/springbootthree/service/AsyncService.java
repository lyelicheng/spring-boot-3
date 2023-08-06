package com.llye.springboot.springbootthree.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public void execute() {
        LOG.info("Start Async method.");
    }
}
