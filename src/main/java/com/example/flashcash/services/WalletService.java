package com.example.flashcash.services;

import com.example.flashcash.models.User;
import com.example.flashcash.models.Wallet;
import com.example.flashcash.repositories.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    public Wallet getWalletByUser(User user){
        return walletRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    public Wallet deposit(User user, Long amount){
        Wallet userWallet = walletRepository.findByUser(user).orElseThrow(() -> new RuntimeException("No wallet found"));
        userWallet.addAmount(amount);
        walletRepository.save(userWallet);
        return userWallet;
    }
}
