package com.example.flashcash.DTO;

import lombok.Getter;

@Getter
public class UserRegisterResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public UserRegisterResponseDto(Long id, String firstName, String lastName, String email){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
