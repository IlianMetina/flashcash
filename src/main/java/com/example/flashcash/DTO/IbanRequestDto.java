package com.example.flashcash.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IbanRequestDto {

    @NotBlank
    @Size(min = 15, max = 34)
    private String iban;
    // Mettre une regex pour s'assurer de la validité de l'IBAN ?
}
