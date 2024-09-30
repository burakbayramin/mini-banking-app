package com.burakbayramin.mini_banking_app.service.impl;

import com.burakbayramin.mini_banking_app.dto.request.TransactionRequest;
import com.burakbayramin.mini_banking_app.dto.response.StatusResponse;
import com.burakbayramin.mini_banking_app.dto.response.TransactionResponse;
import com.burakbayramin.mini_banking_app.exception.BadRequestException;
import com.burakbayramin.mini_banking_app.exception.ResourceNotFoundException;
import com.burakbayramin.mini_banking_app.mapper.TransactionMapper;
import com.burakbayramin.mini_banking_app.model.Account;
import com.burakbayramin.mini_banking_app.model.Transaction;
import com.burakbayramin.mini_banking_app.model.TransactionStatus;
import com.burakbayramin.mini_banking_app.repository.AccountRepository;
import com.burakbayramin.mini_banking_app.repository.TransactionRepository;
import com.burakbayramin.mini_banking_app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;
    private final UserServiceImpl userService;

    /**
     * Initiates a money transfer from one account to another.
     *
     * @param userId  The UUID of the user initiating the transfer.
     * @param request The TransactionRequest containing transfer details.
     * @throws ResourceNotFoundException if either the source or destination account is not found.
     * @throws BadRequestException       if the user does not have permission, attempts to transfer to the same account,
     *                                   or has insufficient balance.
     */
    @Override
    @Transactional
    public void initiateMoneyTransfer(UUID userId, TransactionRequest request) {

        // Retrieve the source account by its ID; throw exception if not found
        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", request.getFromAccountId()));

        // Retrieve the destination account by its ID; throw exception if not found
        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", request.getToAccountId()));

        // Verify that the source account belongs to the initiating user
        if (!fromAccount.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to send from this account.");
        }

        // Prevent transferring to the same account
        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new BadRequestException("Cannot transfer to the same account.");
        }

        // Check if the source account has sufficient balance for the transfer
        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BadRequestException("Insufficient balance in the from account.");
        }

        Transaction transaction = transactionMapper.toTransaction(request, fromAccount, toAccount);
        transaction.setStatus(TransactionStatus.SUCCESS);

        // Update account balances
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        // Save the transaction and updated account balances to the database
        transactionRepository.save(transaction);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

    }

    /**
     * Retrieves the transaction history for a specific account belonging to a user.
     *
     * @param userId    The UUID of the user requesting the transaction history.
     * @param accountId The UUID of the account for which to retrieve transactions.
     * @return A list of TransactionResponse DTOs representing the transaction history.
     * @throws ResourceNotFoundException if the account is not found.
     * @throws BadRequestException       if the user does not have permission to view the account's transactions.
     */
    @Override
    @Transactional
    public List<TransactionResponse> viewTransactionHistory(UUID userId, UUID accountId) {

        // Retrieve the account by its ID; throw exception if not found
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        // Verify that the account belongs to the requesting user
        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to view this account's transactions.");
        }

        // Retrieve all transactions where the account is either the source or destination
        List<Transaction> transactions = transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);

        return transactions.stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

}
