package com.example.user_management.service;

import com.example.user_management.domain.User;
import com.example.user_management.dto.UserResponse;
import com.example.user_management.repository.UserRepository;
import com.example.user_management.dto.UserRequest;
import com.example.user_management.exception.DuplicateResourceException;
import com.example.user_management.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()))
                .toList();
    }

    public UserResponse findUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    public UserResponse createUser(UserRequest userRequest) {
        // Basic password requirement on create
        if (userRequest.password() == null || userRequest.password().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        // Duplicate checks
        if (userRepository.existsByUsernameIgnoreCase(userRequest.username())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (userRepository.existsByEmailIgnoreCase(userRequest.email())) {
            throw new DuplicateResourceException("Email already exists");
        }
        var user = User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();
        var savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public UserResponse updateUser(UserRequest userRequest) {
        if (userRequest.id() == null) {
            throw new IllegalArgumentException("Id is required");
        }
        var user = userRepository.findById(userRequest.id())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Duplicate checks excluding this id
        if (userRepository.existsByUsernameIgnoreCaseAndIdNot(userRequest.username(), user.getId())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (userRepository.existsByEmailIgnoreCaseAndIdNot(userRequest.email(), user.getId())) {
            throw new DuplicateResourceException("Email already exists");
        }

        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        if (userRequest.password() != null && !userRequest.password().isBlank()) {
            user.setPassword(userRequest.password());
        } // else keep the existing password

        var updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
