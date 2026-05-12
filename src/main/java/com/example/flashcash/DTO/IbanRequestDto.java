package com.example.flashcash.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IbanRequestDto {

    @NotBlank
    @Size(min = 15, max = 34)
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z0-9]+$", message = "IBAN invalide")
    private String iban;
    // Mettre une regex pour s'assurer de la validité de l'IBAN ?
}
