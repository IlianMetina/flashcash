package com.example.flashcash.DTO;

import com.example.flashcash.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FriendRequestDto {

    @NotBlank
    private String recipient;
}
