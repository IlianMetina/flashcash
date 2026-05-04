package com.example.flashcash.controllers;

import com.example.flashcash.services.WalletService;
import org.springframework.stereotype.Controller;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }


}
