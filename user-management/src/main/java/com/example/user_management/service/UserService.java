package com.example.user_management.service;

import com.example.user_management.domain.User;
import com.example.user_management.dto.UserResponse;
import com.example.user_management.repository.UserRepository;
import com.example.user_management.dto.UserRequest;
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
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    public UserResponse createUser(UserRequest userRequest) {
        var user = User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();
        var savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public UserResponse updateUser(UserRequest userRequest) {
        var user = userRepository.findById(userRequest.id())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        var updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
