package com.example.user_management;

import com.example.user_management.dto.UserResponse;
import com.example.user_management.resource.UserResource;
import com.example.user_management.service.UserService;
import com.example.user_management.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserResource.class)
@AutoConfigureMockMvc(addFilters = false)
class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnAllUsers() throws Exception {
        // Arrange
        var users = List.of(
                new UserResponse(1L, "alice", "alice@example.com"),
                new UserResponse(2L, "bob", "bob@example.com")
        );
        given(userService.findAllUsers()).willReturn(users);

        // Act + Assert
        mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("alice"))
                .andExpect(jsonPath("$[0].email").value("alice@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("bob"))
                .andExpect(jsonPath("$[1].email").value("bob@example.com"));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        // Arrange
        var user = new UserResponse(1L, "alice", "alice@example.com");
        given(userService.findUserById(1L)).willReturn(user);
        // Act + Assert
        mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void shouldReturnNotFoundForNonExistingUser() throws Exception {
        // Arrange
        given(userService.findUserById(999L)).willThrow(new ResourceNotFoundException("User not found"));
        // Act + Assert
        mockMvc.perform(get("/users/999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateUserReturns201AndLocation() throws Exception {
        // service returns created user with id
        given(userService.createUser(any())).willReturn(new UserResponse(10L, "alice", "alice@example.com"));

        String body = "{" +
                "\"username\":\"alice\"," +
                "\"email\":\"alice@example.com\"," +
                "\"password\":\"Secret123!\"}";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/users/10")))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.username").value("alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void deleteUserReturns204AndUserIsNotFound() throws Exception {
        // Arrange: user exists on first fetch, then is gone
        given(userService.findUserById(5L))
                .willReturn(new UserResponse(5L, "charlie", "charlie@example.com"))
                .willThrow(new ResourceNotFoundException("User not found"));
        doNothing().when(userService).deleteUser(5L);

        // Precondition: confirm user exists
        mockMvc.perform(get("/users/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.username").value("charlie"))
                .andExpect(jsonPath("$.email").value("charlie@example.com"));

        // Act: delete user
        mockMvc.perform(delete("/users/5"))
                .andExpect(status().isNoContent());

        // verify delete was called
        verify(userService).deleteUser(5L);

        // Assert: user no longer exists
        mockMvc.perform(get("/users/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
