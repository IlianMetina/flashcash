package com.example.flashcash.DTO;

import com.example.flashcash.models.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionsHistory {

    private String firstName;
    private String lastName;
    private Long amount;
    private TransactionType type;
    private LocalDateTime date;

}
