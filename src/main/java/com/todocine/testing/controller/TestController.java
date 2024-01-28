package com.todocine.testing.controller;

import com.todocine.testing.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing")
public class TestController {

    @Autowired
    private TestService testService;

    @Profile("test")
    @PostMapping("/reset")
    public void resetDatabase() {
        testService.deleteAll();
    }

}
