package com.example.flashcash.controllers;

import com.example.flashcash.models.User;
import com.example.flashcash.services.TransactionService;
import com.example.flashcash.services.UserService;
import com.example.flashcash.services.WalletService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminController {

    private final UserService userService;
    private final WalletService walletService;
    private final TransactionService transactionService;

    public AdminController(UserService userService, WalletService walletService, TransactionService transactionService){
        this.userService = userService;
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @GetMapping("/admin")
    public String adminPage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("userInfos", userService.findUserByEmail(user.getEmail()));
        model.addAttribute("transactionsInfos", transactionService.allTransactionsAmount());
        model.addAttribute("usersCount", userService.countAllUsers());
        model.addAttribute("transactionsHistory", transactionService.transactionsHistory());
        return "admin";
    }


}
