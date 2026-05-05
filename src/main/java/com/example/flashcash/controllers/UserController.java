package com.example.flashcash.controllers;

import com.example.flashcash.models.User;
import com.example.flashcash.models.Wallet;
import com.example.flashcash.services.UserService;
import com.example.flashcash.services.WalletService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final WalletService walletService;

    public UserController(UserService userService, WalletService walletService){
        this.userService = userService;
        this.walletService = walletService;
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal User user, Model model){
        Wallet wallet = walletService.getWalletByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("wallet", wallet);
        return "profile";
    }


}
