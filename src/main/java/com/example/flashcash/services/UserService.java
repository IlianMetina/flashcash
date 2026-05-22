package com.example.flashcash.services;

import com.example.flashcash.models.Role;
import com.example.flashcash.models.User;
import com.example.flashcash.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public int countAllUsers(){
        List<User> allUsers = userRepository.findAll();
        int count = 0;
        for(int i = 0; i < allUsers.size(); i++){
            count++;
        }

        return count;
    }
}
