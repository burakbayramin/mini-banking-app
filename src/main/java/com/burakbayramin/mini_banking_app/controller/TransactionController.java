package com.burakbayramin.mini_banking_app.controller;

import com.burakbayramin.mini_banking_app.dto.request.TransactionRequest;
import com.burakbayramin.mini_banking_app.dto.response.TransactionResponse;
import com.burakbayramin.mini_banking_app.service.impl.TransactionServiceImpl;
import com.burakbayramin.mini_banking_app.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.responses.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.burakbayramin.mini_banking_app.constants.PathConstants.*;

@RestController
@RequestMapping(TRANSACTIONS)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionServiceImpl transactionService;
    private final UserServiceImpl userService;

    @PostMapping(TRANSFER)
    public ResponseEntity<TransactionResponse> initiateMoneyTransfer(@Validated @RequestBody TransactionRequest request,
                                                                     Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        TransactionResponse response = transactionService.initiateMoneyTransfer(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(TRANSACTION_HISTORY)
    public ResponseEntity<List<TransactionResponse>> viewTransactionHistory(@PathVariable("accountId") UUID accountId,
                                                                            Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        List<TransactionResponse> transactions = transactionService.viewTransactionHistory(userId, accountId);
        return ResponseEntity.ok(transactions);
    }
}
