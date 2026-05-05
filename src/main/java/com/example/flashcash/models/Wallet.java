package com.example.flashcash.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String currency = "EUR";
    private Long balance;
    private String iban;

    // Calcul de la balance en centimes

    public void addAmount(Long amount){
        if(amount <= 0) throw new IllegalArgumentException("Le montant à créditer doit être positif");
        this.balance += amount;
    }

    public void minusAmount(Long amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Le montant à créditer doit être positif");
        }
        if(this.balance < amount){
            throw new IllegalArgumentException("Solde insuffisant");
        }
        this.balance -= amount;
    }
}