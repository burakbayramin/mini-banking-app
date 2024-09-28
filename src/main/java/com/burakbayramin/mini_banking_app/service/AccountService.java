package com.burakbayramin.mini_banking_app.service;

import com.burakbayramin.mini_banking_app.dto.request.AccountCreateRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountSearchRequest;
import com.burakbayramin.mini_banking_app.dto.request.AccountUpdateRequest;
import com.burakbayramin.mini_banking_app.dto.response.AccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    /**
     * Yeni bir hesap oluşturur.
     *
     * @param userId  Hesap oluşturacak kullanıcının UUID ID'si
     * @param request Hesap oluşturma isteği DTO'su
     * @return Oluşturulan hesabın detayları DTO'su
     */
    AccountResponse createAccount(UUID userId, AccountCreateRequest request);

    /**
     * Hesapları arar ve filtreler.
     *
     * @param userId  Hesapları arayan kullanıcının UUID ID'si
     * @param request Arama isteği DTO'su
     * @return Arama sonuçları DTO'larının listesi
     */
    List<AccountResponse> searchAccounts(UUID userId, AccountSearchRequest request);

    /**
     * Hesap bilgilerini günceller.
     *
     * @param userId    Hesap güncelleyecek kullanıcının UUID ID'si
     * @param accountId Güncellenecek hesabın UUID ID'si
     * @param request   Hesap güncelleme isteği DTO'su
     * @return Güncellenen hesabın detayları DTO'su
     */
    AccountResponse updateAccount(UUID userId, UUID accountId, AccountUpdateRequest request);

    /**
     * Hesabı siler.
     *
     * @param userId    Hesabı silmek isteyen kullanıcının UUID ID'si
     * @param accountId Silinecek hesabın UUID ID'si
     */
    void deleteAccount(UUID userId, UUID accountId);

    /**
     * Hesap detaylarını getirir.
     *
     * @param userId    Hesap detaylarını görmek isteyen kullanıcının UUID ID'si
     * @param accountId Detayları getirilecek hesabın UUID ID'si
     * @return Hesap detayları DTO'su
     */
    AccountResponse getAccountDetails(UUID userId, UUID accountId);
}
