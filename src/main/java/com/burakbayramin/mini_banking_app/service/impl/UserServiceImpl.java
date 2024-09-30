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

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param request The user registration request containing username, password, and email.
     * @return The saved User entity after successful registration.
     * @throws UserAlreadyExistsException if the username or email is already taken.
     */
    @Override
    public User registerUser(UserRegisterRequest request) {

        // Check if the username is already taken
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken!");
        }

        // Check if the email is already in use
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use!");
        }

        // Create a new User entity using the builder pattern
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        return userRepository.save(user);
    }

    /**
     * Retrieves the UUID of the currently authenticated user.
     *
     * @param authentication The Authentication object containing the user's authentication details.
     * @return The UUID of the authenticated user.
     * @throws RuntimeException if the user is not found in the repository.
     */
    @Override
    public UUID getUserIdFromAuthentication(Authentication authentication) {

        // Extract the username from the Authentication object
        String username = authentication.getName();

        // Retrieve the User entity by username; throw an exception if not found
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with the given username: " + username));

        return user.getId();
    }
}
