package com.todocine.controller.testing;

import com.todocine.dao.UsuarioDAO;
import com.todocine.service.testing.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Profile("test")
@RequestMapping("/testing")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/reset")
    public void resetDatabase() {
        testService.deleteAll();
    }

}
