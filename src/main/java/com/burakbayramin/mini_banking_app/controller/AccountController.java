package com.burakbayramin.mini_banking_app.controller;

import com.burakbayramin.mini_banking_app.dto.request.AccountCreateRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountSearchRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountUpdateRequest;
import com.burakbayramin.mini_banking_app.dto.response.AccountResponse;
import com.burakbayramin.mini_banking_app.service.impl.AccountServiceImpl;
import com.burakbayramin.mini_banking_app.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.burakbayramin.mini_banking_app.constants.PathConstants.*;

@RestController
@RequestMapping(ACCOUNTS)
@RequiredArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountService;
    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Validated @RequestBody AccountCreateRequest request,
                                                         Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        AccountResponse response = accountService.createAccount(userId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(SEARCH_ACCOUNTS)
    public ResponseEntity<List<AccountResponse>> searchAccounts(@RequestBody AccountSearchRequest request,
                                                                Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        List<AccountResponse> responses = accountService.searchAccounts(userId, request);
        return ResponseEntity.ok(responses);
    }

    @PutMapping(ACCOUNT_ID)
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable("id") UUID accountId,
                                                         @Validated @RequestBody AccountUpdateRequest request,
                                                         Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        AccountResponse response = accountService.updateAccount(userId, accountId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(ACCOUNT_ID)
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") UUID accountId,
                                              Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        accountService.deleteAccount(userId, accountId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(ACCOUNT_ID)
    public ResponseEntity<AccountResponse> getAccountDetails(@PathVariable("id") UUID accountId,
                                                             Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        AccountResponse response = accountService.getAccountDetails(userId, accountId);
        return ResponseEntity.ok(response);
    }
}
