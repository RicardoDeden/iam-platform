package com.example.user_management.dto;

public record UserRequest(
        Long id,
        String username,
        String email,
        String password
) {
}
