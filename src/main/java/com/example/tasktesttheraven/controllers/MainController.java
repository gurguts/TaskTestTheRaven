package com.example.tasktesttheraven.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The controller responsible for displaying the main page
 */
@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String getMainPage() {
        return "main";
    }
}
