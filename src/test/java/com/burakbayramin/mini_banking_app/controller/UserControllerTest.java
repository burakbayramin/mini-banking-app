package com.burakbayramin.mini_banking_app.controller;

import com.burakbayramin.mini_banking_app.dto.request.UserLoginRequest;
import com.burakbayramin.mini_banking_app.dto.request.UserRegisterRequest;
import com.burakbayramin.mini_banking_app.model.User;
import com.burakbayramin.mini_banking_app.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterUser_Success() throws Exception {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setUsername("john_doe");
        registerRequest.setPassword("SecurePass123");
        registerRequest.setEmail("john@example.com");

        User mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername(registerRequest.getUsername());
        mockUser.setEmail(registerRequest.getEmail());
        mockUser.setCreatedAt(LocalDateTime.now());
        mockUser.setUpdatedAt(LocalDateTime.now());

        Mockito.when(userService.registerUser(any(UserRegisterRequest.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testRegisterUser_InvalidInput() throws Exception {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setUsername(""); // Invalid username
        registerRequest.setPassword("123"); // Weak password
        registerRequest.setEmail("invalid-email"); // Invalid email

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

}
