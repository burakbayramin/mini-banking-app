package com.burakbayramin.mini_banking_app.repository;

import com.burakbayramin.mini_banking_app.model.Account;
import com.burakbayramin.mini_banking_app.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountIdOrToAccountId(UUID fromAccountId, UUID toAccountId);

    List<Transaction> findByFromAccountId(UUID fromAccountId);

    List<Transaction> findByToAccountId(UUID toAccountId);
}
