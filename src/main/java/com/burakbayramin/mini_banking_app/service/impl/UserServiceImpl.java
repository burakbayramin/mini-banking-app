package com.burakbayramin.mini_banking_app.service.impl;

import com.burakbayramin.mini_banking_app.dto.request.UserRegisterRequest;
import com.burakbayramin.mini_banking_app.exception.ResourceNotFoundException;
import com.burakbayramin.mini_banking_app.exception.UserAlreadyExistsException;
import com.burakbayramin.mini_banking_app.model.User;
import com.burakbayramin.mini_banking_app.repository.UserRepository;
import com.burakbayramin.mini_banking_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserRegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken!");
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use!");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        return userRepository.save(user);
    }

    @Override
    public UUID getUserIdFromAuthentication(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with the given username: " + username));
        return user.getId();
    }
}
