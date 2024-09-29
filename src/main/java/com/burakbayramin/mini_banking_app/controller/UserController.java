package com.burakbayramin.mini_banking_app.controller;

import com.burakbayramin.mini_banking_app.config.JwtUtils;
import com.burakbayramin.mini_banking_app.constants.MessageConstants;
import com.burakbayramin.mini_banking_app.dto.request.UserLoginRequest;
import com.burakbayramin.mini_banking_app.dto.request.UserRegisterRequest;
import com.burakbayramin.mini_banking_app.dto.response.LoginResponse;
import com.burakbayramin.mini_banking_app.dto.response.StatusResponse;
import com.burakbayramin.mini_banking_app.exception.BadRequestException;
import com.burakbayramin.mini_banking_app.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.burakbayramin.mini_banking_app.constants.PathConstants.*;

@Tag(
        name = "REST APIs for Users in Mini Banking App",
        description = "Endpoints for user registration and authentication."
)
@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with the provided username, password, and email."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid registration details"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(REGISTER)
    public ResponseEntity<StatusResponse> registerUser(@Validated @RequestBody UserRegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new StatusResponse(MessageConstants.USER_REGISTERED, MessageConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Authenticate a user",
            description = "Authenticates a user with the provided username and password and returns a JWT token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid username or password"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponse> authenticateUser(@Validated @RequestBody UserLoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userDetails);

            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Invalid username or password");
        }
    }
}

