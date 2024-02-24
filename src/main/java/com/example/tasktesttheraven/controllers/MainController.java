package com.example.tasktesttheraven.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The controller responsible for displaying the main page
 */

@Controller
@RequestMapping("/")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping
    public String getMainPage() {
        logger.info("Accessed main page");
        return "main";
    }
}

