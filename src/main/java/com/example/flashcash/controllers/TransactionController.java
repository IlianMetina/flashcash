package com.example.flashcash.controllers;

import com.example.flashcash.DTO.TransferRequestDto;
import com.example.flashcash.models.User;
import com.example.flashcash.models.Wallet;
import com.example.flashcash.services.TransactionService;
import com.example.flashcash.services.UserService;
import com.example.flashcash.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final WalletService walletService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, WalletService walletService, UserService userService){
        this.transactionService = transactionService;
        this.walletService = walletService;
        this.userService = userService;
    }

    @GetMapping("/transfers")
    public String transferPage(@AuthenticationPrincipal User user, Model model){
        Wallet userWallet = walletService.getWalletByUser(user);
        model.addAttribute("balance", userWallet.getBalance());
        model.addAttribute("transferRequestDto", new TransferRequestDto());
        return "transaction/transfer";
    }

    @PostMapping("/transfers")
    public String transfers(@Valid @ModelAttribute TransferRequestDto transferRequestDto, BindingResult result, @AuthenticationPrincipal User user, Model model){
        if(result.hasErrors()) return "transaction/transfer";
        try{
            transactionService.transfer(user, transferRequestDto);
        } catch (RuntimeException e) {
            model.addAttribute("error", e);
            return "transaction/transfer";
        }
        model.addAttribute("transferRequestDto", new TransferRequestDto());
        return "redirect:/profile";
    }

}
