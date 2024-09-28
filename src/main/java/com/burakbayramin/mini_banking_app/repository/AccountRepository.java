package com.burakbayramin.mini_banking_app.repository;

import com.burakbayramin.mini_banking_app.model.Account;
import com.burakbayramin.mini_banking_app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
//    /**
//     * Belirli bir kullanıcıya ait tüm hesapları getirir.
//     *
//     * @param userId Kullanıcının UUID'si
//     * @return Hesapların listesi
//     */
//    List<Account> findByUserId(UUID userId);

    /**
     * Belirli bir kullanıcıya ait tüm hesapları sayfalama desteği ile getirir.
     *
     * @param userId   Kullanıcının UUID'si
     * @param pageable Sayfalama bilgilerini içerir
     * @return Sayfalı hesap listesi
     */
    Page<Account> findByUserId(UUID userId, Pageable pageable);

    /**
     * Belirli bir kullanıcıya ait belirli bir hesabı getirir.
     *
     * @param id     Hesabın UUID'si
     * @param userId Kullanıcının UUID'si
     * @return Hesap mevcutsa Optional<Account> döner
     */
    Optional<Account> findByIdAndUserId(UUID id, UUID userId);

    /**
     * Belirtilen hesap numarasına sahip bir hesabın var olup olmadığını kontrol eder.
     *
     * @param number Kontrol edilecek hesap numarası
     * @return Hesap mevcutsa true, değilse false döner
     */
    boolean existsByNumber(String number);

    Optional<Account> findByNumber(String number);
    Optional<Account> findByName(String name);
    Boolean existsByName(String name);
    List<Account> findByUserId(UUID userId);
    List<Account> findByUserIdAndNumberContainingAndNameContaining(UUID userId, String number, String name);
}
