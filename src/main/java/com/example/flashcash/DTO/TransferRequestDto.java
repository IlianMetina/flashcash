package com.example.flashcash.DTO;

import lombok.Data;

@Data
public class TransferRequestDto {
    private String recipient;
    private Long amount;

}
