package com.burakbayramin.mini_banking_app.controller;

import com.burakbayramin.mini_banking_app.constants.MessageConstants;
import com.burakbayramin.mini_banking_app.dto.request.TransactionRequest;
import com.burakbayramin.mini_banking_app.dto.response.StatusResponse;
import com.burakbayramin.mini_banking_app.dto.response.TransactionResponse;
import com.burakbayramin.mini_banking_app.service.impl.TransactionServiceImpl;
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
        name = "REST APIs for Transactions in Mini Banking App",
        description = "Endpoints for money transfers and transaction history."
)
@RestController
@RequestMapping(TRANSACTIONS)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionServiceImpl transactionService;
    private final UserServiceImpl userService;

    @Operation(
            summary = "Initiate a Money Transfer",
            description = "Transfers a specified amount from one account to another for the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction successfully initiated"),
            @ApiResponse(responseCode = "400", description = "Invalid transaction request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "One or both accounts not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(TRANSFER)
    public ResponseEntity<StatusResponse> initiateMoneyTransfer(@Validated @RequestBody TransactionRequest request,
                                                                     Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        transactionService.initiateMoneyTransfer(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new StatusResponse(MessageConstants.TRANSACTION_MESSAGE_201, MessageConstants.MESSAGE_201));
    }

    @Operation(
            summary = "View Transaction History",
            description = "Retrieves the transaction history for a specific account belonging to the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction history successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(TRANSACTION_HISTORY)
    public ResponseEntity<List<TransactionResponse>> viewTransactionHistory(@PathVariable("accountId") UUID accountId,
                                                                            Authentication authentication) {
        UUID userId = userService.getUserIdFromAuthentication(authentication);
        List<TransactionResponse> transactions = transactionService.viewTransactionHistory(userId, accountId);
        return ResponseEntity.ok(transactions);
    }
}
