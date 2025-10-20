package com.example.user_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        Long id,
        @NotBlank @Size(min = 3, max = 100) String username,
        @NotBlank @Email String email,
        String password
) {
}
