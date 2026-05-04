package com.example.flashcash.controllers;

import com.example.flashcash.DTO.RegisterRequestDto;
import com.example.flashcash.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequestDto requestDto){
        authService.registerUser(requestDto);
        return "redirect:/login";
    }
}
