package com.burakbayramin.mini_banking_app.service;

import com.burakbayramin.mini_banking_app.dto.request.TransactionRequest;
import com.burakbayramin.mini_banking_app.dto.response.StatusResponse;
import com.burakbayramin.mini_banking_app.dto.response.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    void initiateMoneyTransfer(UUID userId, TransactionRequest request);

    List<TransactionResponse> viewTransactionHistory(UUID userId, UUID accountId);
}
