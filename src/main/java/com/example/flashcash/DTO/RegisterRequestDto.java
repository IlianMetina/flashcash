package com.example.flashcash.DTO;

import lombok.Data;

@Data
public class RegisterRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String address;
    private String country;
    private String phoneNumber;
}
