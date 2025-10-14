package com.example.user_management.resource;

import com.example.user_management.dto.UserRequest;
import com.example.user_management.dto.UserResponse;
import com.example.user_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping
    public UserResponse updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
