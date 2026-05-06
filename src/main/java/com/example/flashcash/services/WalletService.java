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
        if(userWallet.getIban() == null || userWallet.getIban().isEmpty()){

        }
        userWallet.addAmount(amount);
        walletRepository.save(userWallet);
        return userWallet;
    }

    public Wallet addIban(User user, String iban){
        Wallet userWallet = walletRepository.findByUser(user).orElseThrow(() -> new RuntimeException("No wallet found"));
        userWallet.setIban(iban);
        walletRepository.save(userWallet);
        return userWallet;
    }

    public String maskIban(String iban){
        if(iban == null || iban.isEmpty()){
            return null;
        }
        return iban.substring(0, 4) + " **** **** **** " + iban.substring(iban.length() - 3);
    }

    public Wallet withdraw(User user, Long amount){
        Wallet userWallet = walletRepository.findByUser(user).orElseThrow(() -> new RuntimeException("No wallet found"));
        userWallet.minusAmount(amount);
        walletRepository.save(userWallet);
        return userWallet;
    }

    public Wallet debit(Wallet userWallet, Long amount){
        userWallet.minusAmount(amount * 100);
        walletRepository.save(userWallet);
        return userWallet;
    }

    public Wallet credit(Wallet userWallet, Long amount){
        userWallet.addAmount(amount * 100);
        walletRepository.save(userWallet);
        return userWallet;
    }
}
