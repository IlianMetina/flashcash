package com.example.flashcash.services;

import com.example.flashcash.DTO.TransferRequestDto;
import com.example.flashcash.models.Transaction;
import com.example.flashcash.models.TransactionType;
import com.example.flashcash.models.User;
import com.example.flashcash.models.Wallet;
import com.example.flashcash.repositories.TransactionRepository;
import com.example.flashcash.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.flashcash.utils.ContactUtils.isEmailOrPhone;

@Service
public class TransactionService {

    private final WalletService walletService;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(WalletService walletService, UserRepository userRepository, TransactionRepository transactionRepository){
        this.walletService = walletService;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction transfer(User user, TransferRequestDto transferRequestDto){

        String method = isEmailOrPhone(transferRequestDto.getRecipient());

        User receiver = null;
        if(method.equals("email")){
            receiver = userRepository.findByEmail(transferRequestDto.getRecipient()).orElseThrow(() -> new RuntimeException("User not found"));
        }else{
            receiver = userRepository.findByPhoneNumber(transferRequestDto.getRecipient()).orElseThrow(() -> new RuntimeException("User not found"));
        }

        Wallet senderWallet = walletService.getWalletByUser(user);
        if(senderWallet.getBalance() < transferRequestDto.getAmount()) throw new RuntimeException("Solde insuffisant");

        Wallet receiverWallet = walletService.getWalletByUser(receiver);
        walletService.credit(receiverWallet, transferRequestDto.getAmount());
        walletService.debit(senderWallet, transferRequestDto.getAmount());

        Transaction transaction = new Transaction();
        transaction.setAmount(transferRequestDto.getAmount());
        transaction.setSender(user);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setReceiver(receiver);
        transactionRepository.save(transaction);

        return transaction;
    }
}
