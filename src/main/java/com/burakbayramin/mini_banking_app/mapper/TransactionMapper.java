package com.burakbayramin.mini_banking_app.mapper;

import com.burakbayramin.mini_banking_app.dto.request.TransactionRequest;
import com.burakbayramin.mini_banking_app.dto.response.TransactionResponse;
import com.burakbayramin.mini_banking_app.model.Account;
import com.burakbayramin.mini_banking_app.model.Transaction;
import com.burakbayramin.mini_banking_app.model.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    /**
     * TransactionCreateRequest DTO'sunu Transaction modeline dönüştürür.
     *
     * @param dto        İşlem oluşturma isteği DTO'su
     * @param fromAccount Gönderen hesap
     * @param toAccount   Alıcı hesap
     * @return Dönüştürülmüş Transaction modeli
     */
    public Transaction toTransaction(TransactionRequest dto, Account fromAccount, Account toAccount) {
        if (dto == null || fromAccount == null || toAccount == null) {
            return null;
        }

        return Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(dto.getAmount())
                .status(TransactionStatus.SUCCESS) // İşlem başarıyla tamamlandı olarak ayarlanır
                .build();
    }

    /**
     * Transaction modelini TransactionResponse DTO'suna dönüştürür.
     *
     * @param transaction İşlem modeli
     * @return Dönüştürülmüş TransactionResponse DTO'su
     */
    public TransactionResponse toTransactionResponse(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return TransactionResponse.builder()
                .id(transaction.getId())
                .fromAccountId(transaction.getFromAccount().getId())
                .toAccountId(transaction.getToAccount().getId())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .status(transaction.getStatus())
                .build();
    }
}