package com.burakbayramin.mini_banking_app.service;

import com.burakbayramin.mini_banking_app.dto.request.TransactionRequest;
import com.burakbayramin.mini_banking_app.dto.response.StatusResponse;
import com.burakbayramin.mini_banking_app.dto.response.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    /**
     * Yeni bir para transferi başlatır.
     *
     * @param userId  İşlemi gerçekleştirecek kullanıcının UUID ID'si
     * @param request Transfer isteği DTO'su
     * @return Oluşturulan işlemin detayları DTO'su
     */
    void initiateMoneyTransfer(UUID userId, TransactionRequest request);

    /**
     * Belirtilen bir hesabın işlem geçmişini getirir.
     *
     * @param userId    Kullanıcının UUID ID'si
     * @param accountId Hesabın UUID ID'si
     * @return İşlem listesi DTO'larının listesi
     */
    List<TransactionResponse> viewTransactionHistory(UUID userId, UUID accountId);
}
