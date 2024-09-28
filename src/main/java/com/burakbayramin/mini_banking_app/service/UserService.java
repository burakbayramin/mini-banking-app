package com.burakbayramin.mini_banking_app.service;

import com.burakbayramin.mini_banking_app.dto.request.UserRegisterRequest;
import com.burakbayramin.mini_banking_app.model.User;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UserService {

    User registerUser(UserRegisterRequest request);

    UUID getUserIdFromAuthentication(Authentication authentication);
}
