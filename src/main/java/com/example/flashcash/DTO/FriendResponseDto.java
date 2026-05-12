package com.example.flashcash.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FriendResponseDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate addedAt;
}
