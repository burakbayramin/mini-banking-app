package com.burakbayramin.mini_banking_app.service.impl;

import com.burakbayramin.mini_banking_app.dto.request.AccountCreateRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountSearchRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountUpdateRequest;
import com.burakbayramin.mini_banking_app.dto.response.AccountResponse;
import com.burakbayramin.mini_banking_app.dto.response.StatusResponse;
import com.burakbayramin.mini_banking_app.exception.BadRequestException;
import com.burakbayramin.mini_banking_app.exception.ConflictException;
import com.burakbayramin.mini_banking_app.exception.ResourceNotFoundException;
import com.burakbayramin.mini_banking_app.mapper.AccountMapper;
import com.burakbayramin.mini_banking_app.model.Account;
import com.burakbayramin.mini_banking_app.model.User;
import com.burakbayramin.mini_banking_app.repository.AccountRepository;
import com.burakbayramin.mini_banking_app.repository.UserRepository;
import com.burakbayramin.mini_banking_app.service.AccountService;
import com.burakbayramin.mini_banking_app.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    /**
     * Creates a new account for the specified user.
     *
     * @param userId  The UUID of the user for whom the account is being created.
     * @param request The AccountCreateRequest containing account creation details.
     * @throws ResourceNotFoundException if the user is not found.
     * @throws ConflictException         if the account number or name already exists.
     */
    @Override
    @Transactional
    public void createAccount(UUID userId, AccountCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (accountRepository.existsByNumber(request.getNumber())) {
            throw new ConflictException("Account number already exists!");
        }

        if (accountRepository.existsByName(request.getName())) {
            throw new ConflictException("Account name already exists!");
        }

        Account account = accountMapper.toAccount(request, user);

         accountRepository.save(account);
    }

    /**
     * Searches for accounts belonging to a user based on provided search criteria.
     *
     * @param userId  The UUID of the user whose accounts are being searched.
     * @param request The AccountSearchRequest containing search parameters.
     * @return A list of AccountResponse DTOs matching the search criteria.
     * @throws ResourceNotFoundException if the user is not found.
     */
    @Override
    @Transactional
    public List<AccountResponse> searchAccounts(UUID userId, AccountSearchRequest request) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Prepare filters; default to empty strings if null to allow partial matching
        String numberFilter = (request.getNumber() != null) ? request.getNumber() : "";
        String nameFilter = (request.getName() != null) ? request.getName() : "";

        // Retrieve accounts matching the user ID and containing the number and name filters
        List<Account> accounts = accountRepository.findByUserIdAndNumberContainingAndNameContaining(userId, numberFilter, nameFilter);

        return accounts.stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    /**
     * Updates the name of an existing account for a user.
     *
     * @param userId    The UUID of the user attempting to update the account.
     * @param accountId The UUID of the account to be updated.
     * @param request   The AccountUpdateRequest containing the new account name.
     * @throws ResourceNotFoundException if the account is not found.
     * @throws BadRequestException       if the user does not own the account.
     * @throws ConflictException         if the new account name already exists.
     */
    @Override
    @Transactional
    public void updateAccount(UUID userId, UUID accountId, AccountUpdateRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to update this account.");
        }

        if (!account.getName().equals(request.getName()) && accountRepository.existsByName(request.getName())) {
            throw new ConflictException("Account name already exists!");
        }

        account.setName(request.getName());
        accountRepository.save(account);
    }

    /**
     * Deletes an existing account for a user.
     *
     * @param userId    The UUID of the user attempting to delete the account.
     * @param accountId The UUID of the account to be deleted.
     * @throws ResourceNotFoundException if the account is not found.
     * @throws BadRequestException       if the user does not own the account.
     */
    @Override
    @Transactional
    public void deleteAccount(UUID userId, UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        // Verify that the account belongs to the user attempting the deletion
        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to delete this account.");
        }

        accountRepository.delete(account);
    }

    /**
     * Retrieves detailed information about a specific account for a user.
     *
     * @param userId    The UUID of the user requesting the account details.
     * @param accountId The UUID of the account to retrieve details for.
     * @return An AccountResponse DTO containing account details.
     * @throws ResourceNotFoundException if the account is not found.
     * @throws BadRequestException       if the user does not own the account.
     */
    @Override
    @Transactional
    public AccountResponse getAccountDetails(UUID userId, UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        // Verify that the account belongs to the user requesting the details
        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to view this account.");
        }

        return accountMapper.toAccountResponse(account);
    }
}

