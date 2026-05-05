package com.example.flashcash.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepositRequestDto {
    @NotNull
    @Min(1)
    private Long amount;
}
