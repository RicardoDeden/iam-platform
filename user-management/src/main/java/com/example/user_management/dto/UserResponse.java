package com.example.user_management.dto;

public record UserResponse(
        Long id,
        String username,
        String email
) {
}
