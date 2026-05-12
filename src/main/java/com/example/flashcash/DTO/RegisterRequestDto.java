package com.example.flashcash.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequestDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
    @NotNull
    private LocalDate birthDate;
    @NotBlank
    private String country;
    @NotBlank
    @Pattern(regexp = "^0[0-9]{9}$", message = "Téléphone invalide")
    private String phoneNumber;
}
