package com.burakbayramin.mini_banking_app.mapper;

import com.burakbayramin.mini_banking_app.dto.request.AccountCreateRequest;
import com.burakbayramin.mini_banking_app.dto.response.AccountResponse;
import com.burakbayramin.mini_banking_app.model.Account;
import com.burakbayramin.mini_banking_app.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountMapper {

    /**
     * AccountCreateRequest DTO'sunu Account modeline dönüştürür.
     * Bakiye her zaman 100 olarak ayarlanır.
     *
     * @param dto  Hesap oluşturma isteği DTO'su
     * @param user Hesap oluşturacak kullanıcı
     * @return Dönüştürülmüş Account modeli
     */
    public Account toAccount(AccountCreateRequest dto, User user) {
        if (dto == null) {
            return null;
        }

        return Account.builder()
                .number(dto.getNumber())
                .name(dto.getName())
                .balance(new BigDecimal("100")) // Bakiye 100 olarak ayarlanır
                .user(user)
                .build();
    }

    /**
     * Account modelini AccountResponse DTO'suna dönüştürür.
     *
     * @param account Hesap modeli
     * @return Dönüştürülmüş AccountResponse DTO'su
     */
    public AccountResponse toAccountResponse(Account account) {
        if (account == null) {
            return null;
        }

        return AccountResponse.builder()
                .id(account.getId())
                .number(account.getNumber())
                .name(account.getName())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }
}
