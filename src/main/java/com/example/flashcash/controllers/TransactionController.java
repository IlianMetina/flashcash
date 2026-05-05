package com.example.flashcash.controllers;

import com.example.flashcash.services.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/transfers")
    public String transferPage(){
        return "transfer";
    }

}
