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
    private final UserServiceImpl userService;
    private final AccountMapper accountMapper;

    /**
     * Yeni bir hesap oluşturur.
     *
     * @param userId  Hesap oluşturacak kullanıcının UUID ID'si
     * @param request Hesap oluşturma isteği DTO'su
     * @return Oluşturulan hesabın detayları DTO'su
     */
    @Override
    @Transactional
    public void createAccount(UUID userId, AccountCreateRequest request) {
        // Kullanıcıyı bul
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Hesap numarası kontrolü
        if (accountRepository.existsByNumber(request.getNumber())) {
            throw new ConflictException("Account number already exists!");
        }

        // Hesap adı kontrolü
        if (accountRepository.existsByName(request.getName())) {
            throw new ConflictException("Account name already exists!");
        }

        // Hesabı oluşturmak için mapper kullanılır, bakiye 100 olarak ayarlanır
        Account account = accountMapper.toAccount(request, user);

        Account savedAccount = accountRepository.save(account);
    }

    /**
     * Hesapları arar ve filtreler.
     *
     * @param userId  Hesapları arayan kullanıcının UUID ID'si
     * @param request Arama isteği DTO'su
     * @return Arama sonuçları DTO'larının listesi
     */
    @Override
    @Transactional
    public List<AccountResponse> searchAccounts(UUID userId, AccountSearchRequest request) {
        String numberFilter = (request.getNumber() != null) ? request.getNumber() : "";
        String nameFilter = (request.getName() != null) ? request.getName() : "";

        List<Account> accounts = accountRepository.findByUserIdAndNumberContainingAndNameContaining(userId, numberFilter, nameFilter);

        return accounts.stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    /**
     * Hesap bilgilerini günceller.
     *
     * @param userId    Hesap güncelleyecek kullanıcının UUID ID'si
     * @param accountId Güncellenecek hesabın UUID ID'si
     * @param request   Hesap güncelleme isteği DTO'su
     * @return Güncellenen hesabın detayları DTO'su
     */
    @Override
    @Transactional
    public void updateAccount(UUID userId, UUID accountId, AccountUpdateRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        // Hesabın kullanıcıya ait olup olmadığını kontrol et
        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to update this account.");
        }

        // Hesap adının benzersizliğini kontrol et
        if (!account.getName().equals(request.getName()) && accountRepository.existsByName(request.getName())) {
            throw new ConflictException("Account name already exists!");
        }

        account.setName(request.getName());
        accountRepository.save(account);
    }

    /**
     * Hesabı siler.
     *
     * @param userId    Hesabı silmek isteyen kullanıcının UUID ID'si
     * @param accountId Silinecek hesabın UUID ID'si
     */
    @Override
    @Transactional
    public void deleteAccount(UUID userId, UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        // Hesabın kullanıcıya ait olup olmadığını kontrol et
        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to delete this account.");
        }

        accountRepository.delete(account);
    }

    /**
     * Hesap detaylarını getirir.
     *
     * @param userId    Hesap detaylarını görmek isteyen kullanıcının UUID ID'si
     * @param accountId Detayları getirilecek hesabın UUID ID'si
     * @return Hesap detayları DTO'su
     */
    @Override
    @Transactional
    public AccountResponse getAccountDetails(UUID userId, UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));

        // Hesabın kullanıcıya ait olup olmadığını kontrol et
        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("You do not have permission to view this account.");
        }

        return accountMapper.toAccountResponse(account);
    }
}

