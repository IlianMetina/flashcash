package com.example.flashcash.controllers;

import com.example.flashcash.DTO.DepositRequestDto;
import com.example.flashcash.models.User;
import com.example.flashcash.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @GetMapping("/deposit")
    public String depositPage(Model model){
        model.addAttribute("depositRequestDto", new DepositRequestDto());
        return "wallet/deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@Valid @ModelAttribute DepositRequestDto dto, BindingResult result, @AuthenticationPrincipal User user){
        if(result.hasErrors()) return "wallet/deposit";
        walletService.deposit(user, dto.getAmount() * 100);
        return "redirect:/profile";
    }

    @GetMapping("/withdraw")
    public String withdrawPage(){
        return "wallet/withdraw";
    }

    @GetMapping("/iban")
    public String ibanPage(){
        return "wallet/iban";
    }
}
