package com.example.flashcash.controllers;

import com.example.flashcash.models.User;
import com.example.flashcash.models.Wallet;
import com.example.flashcash.services.WalletService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController {

    private final WalletService walletService;

    public HomeController(WalletService walletService){
        this.walletService = walletService;
    }

    @GetMapping("/home")
    public String homePage(@AuthenticationPrincipal User user, Model model){
        Wallet wallet = walletService.getWalletByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("wallet", wallet);
        return "home";
    }


}
