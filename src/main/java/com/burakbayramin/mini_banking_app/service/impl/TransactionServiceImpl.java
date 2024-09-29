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

    @Override
    @Transactional
    public void initiateMoneyTransfer(UUID userId, TransactionRequest request) {
        // Gönderen hesabı bul
        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", request.getFromAccountId()));

        // Alıcı hesabı bul
        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", request.getToAccountId()));

        // Gönderen hesabın sahibi mi kontrol et
        if (!fromAccount.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to send from this account.");
        }

        // Gönderen ve alıcı hesap aynı mı kontrol et
        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new BadRequestException("Cannot transfer to the same account.");
        }

        // Gönderen hesabın bakiyesi yeterli mi kontrol et
        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BadRequestException("Insufficient balance in the from account.");
        }

        // İşlemi oluştur
        Transaction transaction = transactionMapper.toTransaction(request, fromAccount, toAccount);
        transaction.setStatus(TransactionStatus.SUCCESS); // İşlem başarıyla tamamlandı

        // Bakiyeleri güncelle
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        // İşlemi kaydet
        Transaction savedTransaction = transactionRepository.save(transaction);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

    }

    /**
     * Belirtilen bir hesabın işlem geçmişini getirir.
     *
     * @param userId    Kullanıcının UUID ID'si
     * @param accountId Hesabın UUID ID'si
     * @return İşlem listesi DTO'larının listesi
     */
    @Override
    @Transactional
    public List<TransactionResponse> viewTransactionHistory(UUID userId, UUID accountId) {
        // Hesabı bul
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        // Hesap sahibi mi kontrol et
        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to view this account's transactions.");
        }

        // İşlem geçmişini getir
        List<Transaction> transactions = transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);

        return transactions.stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

}
