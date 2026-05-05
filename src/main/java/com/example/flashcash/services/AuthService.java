package com.example.flashcash.services;

import com.example.flashcash.DTO.LoginRequestDto;
import com.example.flashcash.DTO.RegisterRequestDto;
import com.example.flashcash.DTO.UserRegisterResponseDto;
import com.example.flashcash.models.Role;
import com.example.flashcash.models.User;
import com.example.flashcash.models.Wallet;
import com.example.flashcash.repositories.UserRepository;
import com.example.flashcash.repositories.WalletRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;

    public AuthService(UserRepository userRepository, WalletRepository walletRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterResponseDto registerUser(RegisterRequestDto requestDto){
        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new RuntimeException("User already exist");
        }

        if(requestDto.getBirthDate().isAfter(LocalDate.now().minusYears(18))){
            throw new RuntimeException("Vous devez être majeur pour utiliser Flash Cash");
        }

        User user = new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(requestDto.getPassword()));
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setRole(Role.USER);
        user.setBirthDate(requestDto.getBirthDate());
        user.setCity(requestDto.getCity());
        user.setAddress(requestDto.getAddress());
        user.setCountry(requestDto.getCountry());

        User registeredUser = userRepository.save(user);

        Wallet userWallet = new Wallet();
        userWallet.setUser(registeredUser);
        userWallet.setBalance(0L);
        userWallet.setCurrency("EUR");
        walletRepository.save(userWallet);
        return new UserRegisterResponseDto(registeredUser.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
