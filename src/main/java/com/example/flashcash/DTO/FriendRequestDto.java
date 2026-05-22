package com.example.flashcash.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FriendRequestDto {

    @NotBlank
    private String recipient;
}
