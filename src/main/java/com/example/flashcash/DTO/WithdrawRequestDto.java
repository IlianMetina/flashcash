package com.example.flashcash.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WithdrawRequestDto {
    @NotNull
    @Min(1)
    private Long amount;
}
