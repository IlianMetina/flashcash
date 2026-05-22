package com.example.flashcash.services;

import com.example.flashcash.DTO.TransactionsHistory;
import com.example.flashcash.DTO.TransactionsInfos;
import com.example.flashcash.DTO.TransferRequestDto;
import com.example.flashcash.models.Transaction;
import com.example.flashcash.models.TransactionType;
import com.example.flashcash.models.User;
import com.example.flashcash.models.Wallet;
import com.example.flashcash.repositories.TransactionRepository;
import com.example.flashcash.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public TransactionsInfos allTransactionsAmount(){
        List<Transaction> allTransactions = transactionRepository.findAll();
        TransactionsInfos transactionsInfos = new TransactionsInfos();
        Long totalAmount = 0L;
        Long count = 0L;
        for(int i = 0; i < allTransactions.size(); i++){
            totalAmount += allTransactions.get(i).getAmount();
            count++;
        }
        transactionsInfos.setCount(count);
        transactionsInfos.setTotalAmount(totalAmount);
        return transactionsInfos;
    }

    public List<TransactionsHistory> transactionsHistory(){
        ArrayList<TransactionsHistory> transactionsHistory = new ArrayList<>();
        List<Transaction> allTransactions = transactionRepository.findAll();
        for(int i = 0; i < Math.min(3, allTransactions.size()); i++){
            TransactionsHistory history = new TransactionsHistory();
            User user = allTransactions.get(i).getSender();
            history.setFirstName(user.getFirstName());
            history.setLastName(user.getLastName());
            history.setDate(allTransactions.get(i).getCreatedAt());
            history.setType(allTransactions.get(i).getType());
            history.setAmount(allTransactions.get(i).getAmount());
            transactionsHistory.add(history);
        }

        return transactionsHistory;
    }



}
