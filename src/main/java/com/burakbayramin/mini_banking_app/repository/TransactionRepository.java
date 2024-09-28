package com.burakbayramin.mini_banking_app.repository;

import com.burakbayramin.mini_banking_app.model.Account;
import com.burakbayramin.mini_banking_app.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Belirli bir hesabın gönderdiği veya aldığı tüm işlemleri getirir.
     *
     * @param fromAccountId Gönderen hesabın UUID'si
     * @param toAccountId   Alıcı hesabın UUID'si
     * @return İşlemlerin listesi
     */
    List<Transaction> findByFromAccountIdOrToAccountId(UUID fromAccountId, UUID toAccountId);

    /**
     * Belirli bir hesaptan gönderilen tüm işlemleri getirir.
     *
     * @param fromAccountId Gönderen hesabın UUID'si
     * @return İşlemlerin listesi
     */
    List<Transaction> findByFromAccountId(UUID fromAccountId);

    /**
     * Belirli bir hesaba gelen tüm işlemleri getirir.
     *
     * @param toAccountId Alıcı hesabın UUID'si
     * @return İşlemlerin listesi
     */
    List<Transaction> findByToAccountId(UUID toAccountId);
}
