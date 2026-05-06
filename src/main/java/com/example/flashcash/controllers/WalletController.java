package com.example.flashcash.controllers;

import com.example.flashcash.DTO.DepositRequestDto;
import com.example.flashcash.DTO.IbanRequestDto;
import com.example.flashcash.DTO.WithdrawRequestDto;
import com.example.flashcash.models.User;
import com.example.flashcash.models.Wallet;
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
    public String depositPage(@AuthenticationPrincipal User user ,Model model){
        Wallet userWallet = walletService.getWalletByUser(user);
        model.addAttribute("depositRequestDto", new DepositRequestDto());
        model.addAttribute("maskedIban", walletService.maskIban(userWallet.getIban()));
        return "wallet/deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@Valid @ModelAttribute DepositRequestDto dto, BindingResult result, @AuthenticationPrincipal User user, Model model){
        if(result.hasErrors()) return "wallet/deposit";
        Wallet userWallet = walletService.getWalletByUser(user);
        walletService.deposit(user, dto.getAmount() * 100);
        model.addAttribute("maskedIban", walletService.maskIban(userWallet.getIban()));
        return "redirect:/profile";
    }

    @GetMapping("/withdraw")
    public String withdrawPage(@AuthenticationPrincipal User user, Model model){
        Wallet userWallet = walletService.getWalletByUser(user);
        model.addAttribute("withdrawRequestDto", new WithdrawRequestDto());
        model.addAttribute("maskedIban", walletService.maskIban(userWallet.getIban()));
        model.addAttribute("balance", userWallet.getBalance());
        return "wallet/withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@Valid @ModelAttribute WithdrawRequestDto withdrawRequestDto, BindingResult result, @AuthenticationPrincipal User user, Model model){
        if(result.hasErrors()) return "wallet/withdraw";
        try{
            walletService.withdraw(user, withdrawRequestDto.getAmount() * 100);
        }catch (IllegalArgumentException e){
            Wallet userWallet = walletService.getWalletByUser(user);
            model.addAttribute("withdrawRequestDto", new WithdrawRequestDto());
            model.addAttribute("balance", userWallet.getBalance());
            model.addAttribute("maskedIban", walletService.maskIban(userWallet.getIban()));
            return "wallet/withdraw";
        }
        return "redirect:/profile";
    }

    @GetMapping("/iban")
    public String ibanPage(Model model){
        model.addAttribute("ibanRequestDto", new IbanRequestDto());
        return "wallet/iban";
    }

    @PostMapping("/iban")
    public String addIban(@Valid @ModelAttribute IbanRequestDto ibanRequestDto, BindingResult result, @AuthenticationPrincipal User user){
        if (result.hasErrors()) return "wallet/iban";
        walletService.addIban(user, ibanRequestDto.getIban());
        return "redirect:/profile";
    }
}
