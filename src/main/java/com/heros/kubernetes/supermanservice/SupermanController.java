package com.heros.kubernetes.supermanservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class SupermanController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupermanController.class);

    @Autowired
    private SupermanConfiguration config;


    @GetMapping
    public String getName(){
        return config.getGreetings();
    }

}
