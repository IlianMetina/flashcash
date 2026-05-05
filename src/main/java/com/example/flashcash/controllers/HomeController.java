package com.example.flashcash.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/contact")
    public String contactPage(){
        return "contact";
    }
}
