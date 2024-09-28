package com.burakbayramin.mini_banking_app.controller;

import com.burakbayramin.mini_banking_app.config.JwtUtils;
import com.burakbayramin.mini_banking_app.constants.MessageConstants;
import com.burakbayramin.mini_banking_app.dto.request.UserLoginRequest;
import com.burakbayramin.mini_banking_app.dto.request.UserRegisterRequest;
import com.burakbayramin.mini_banking_app.dto.response.LoginResponse;
import com.burakbayramin.mini_banking_app.dto.response.StatusResponse;
import com.burakbayramin.mini_banking_app.exception.BadRequestException;
import com.burakbayramin.mini_banking_app.model.User;
import com.burakbayramin.mini_banking_app.service.UserService;
import com.burakbayramin.mini_banking_app.service.impl.CustomUserDetailsService;
import com.burakbayramin.mini_banking_app.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.burakbayramin.mini_banking_app.constants.PathConstants.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping(REGISTER)
    public ResponseEntity<StatusResponse> registerUser(@Validated @RequestBody UserRegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new StatusResponse(MessageConstants.USER_REGISTERED, MessageConstants.MESSAGE_201));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponse> authenticateUser(@Validated @RequestBody UserLoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Eğer kimlik doğrulama başarılıysa, SecurityContext'e yerleştir
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT token oluştur
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userDetails);

            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Invalid username or password");
        }
    }
}

