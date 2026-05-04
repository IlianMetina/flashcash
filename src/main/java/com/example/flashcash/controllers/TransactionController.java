package com.example.flashcash.controllers;

import com.example.flashcash.services.TransactionService;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

}
