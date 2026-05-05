package com.example.flashcash.repositories;

import com.example.flashcash.models.User;
import com.example.flashcash.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUser(User user);
}

