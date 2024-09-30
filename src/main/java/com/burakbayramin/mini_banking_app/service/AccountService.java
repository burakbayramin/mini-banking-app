package com.burakbayramin.mini_banking_app.service;

import com.burakbayramin.mini_banking_app.dto.request.AccountCreateRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountSearchRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountUpdateRequest;
import com.burakbayramin.mini_banking_app.dto.response.AccountResponse;
import com.burakbayramin.mini_banking_app.dto.response.StatusResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {


    void createAccount(UUID userId, AccountCreateRequest request);

    List<AccountResponse> searchAccounts(UUID userId, AccountSearchRequest request);

    void updateAccount(UUID userId, UUID accountId, AccountUpdateRequest request);

    void deleteAccount(UUID userId, UUID accountId);

    AccountResponse getAccountDetails(UUID userId, UUID accountId);
}
