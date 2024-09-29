package com.burakbayramin.mini_banking_app.controller;

import com.burakbayramin.mini_banking_app.constants.MessageConstants;
import com.burakbayramin.mini_banking_app.dto.request.AccountCreateRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountSearchRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountUpdateRequest;
import com.burakbayramin.mini_banking_app.dto.response.AccountResponse;
import com.burakbayramin.mini_banking_app.dto.response.StatusResponse;
import com.burakbayramin.mini_banking_app.service.impl.AccountServiceImpl;
import com.burakbayramin.mini_banking_app.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.burakbayramin.mini_banking_app.constants.PathConstants.*;

@Tag(
        name = "REST APIs for Accounts in Mini Banking App",
        description = "Endpoints for managing user accounts."
)
@RestController
@RequestMapping(ACCOUNTS)
@RequiredArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountService;
    private final UserServiceImpl userService;

    @Operation(
            summary = "Create a new account",
            description = "Creates a new account for the authenticated user with the provided account details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid account creation request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<StatusResponse> createAccount(@Validated @RequestBody AccountCreateRequest request,
                                                         Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        accountService.createAccount(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new StatusResponse(MessageConstants.ACCOUNT_CREATE_201, MessageConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Search for accounts",
            description = "Searches for accounts belonging to the authenticated user based on provided search criteria."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid account search request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(SEARCH_ACCOUNTS)
    public ResponseEntity<List<AccountResponse>> searchAccounts(@RequestBody AccountSearchRequest request,
                                                                Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        List<AccountResponse> responses = accountService.searchAccounts(userId, request);
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Update an existing account",
            description = "Updates the details of an existing account identified by account ID for the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid account update request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(ACCOUNT_ID)
    public ResponseEntity<StatusResponse> updateAccount(@PathVariable("id") UUID accountId,
                                                         @Validated @RequestBody AccountUpdateRequest request,
                                                         Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        accountService.updateAccount(userId, accountId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StatusResponse(MessageConstants.ACCOUNT_UPDATE_201, MessageConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Delete an account",
            description = "Deletes an existing account identified by account ID for the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(ACCOUNT_ID)
    public ResponseEntity<StatusResponse> deleteAccount(@PathVariable("id") UUID accountId,
                                              Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        accountService.deleteAccount(userId, accountId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StatusResponse(MessageConstants.ACCOUNT_DELETE_201, MessageConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Get account details",
            description = "Retrieves the details of an existing account identified by account ID for the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(ACCOUNT_ID)
    public ResponseEntity<AccountResponse> getAccountDetails(@PathVariable("id") UUID accountId,
                                                             Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        AccountResponse response = accountService.getAccountDetails(userId, accountId);
        return ResponseEntity.ok(response);
    }
}
