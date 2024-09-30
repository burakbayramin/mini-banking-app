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
//    List<Account> findByUserId(UUID userId);

    Page<Account> findByUserId(UUID userId, Pageable pageable);

    Optional<Account> findByIdAndUserId(UUID id, UUID userId);

    boolean existsByNumber(String number);

    Optional<Account> findByNumber(String number);
    Optional<Account> findByName(String name);
    Boolean existsByName(String name);
    List<Account> findByUserId(UUID userId);
    List<Account> findByUserIdAndNumberContainingAndNameContaining(UUID userId, String number, String name);
}
