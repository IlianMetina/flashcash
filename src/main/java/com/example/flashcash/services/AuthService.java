package com.example.flashcash.services;

import com.example.flashcash.DTO.LoginRequestDto;
import com.example.flashcash.DTO.RegisterRequestDto;
import com.example.flashcash.DTO.UserRegisterResponseDto;
import com.example.flashcash.models.Role;
import com.example.flashcash.models.User;
import com.example.flashcash.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterResponseDto registerUser(RegisterRequestDto requestDto){
        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new RuntimeException("User already exist");
        }

        User user = new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(requestDto.getPassword()));
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setRole(Role.USER);
        user.setCity(requestDto.getCity());
        user.setAddress(requestDto.getAddress());
        user.setCity(requestDto.getCity());

        User registeredUser = userRepository.save(user);
        return new UserRegisterResponseDto(registeredUser.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
