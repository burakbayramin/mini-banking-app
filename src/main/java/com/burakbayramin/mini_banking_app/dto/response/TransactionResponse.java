package com.burakbayramin.mini_banking_app.dto.response;
import com.burakbayramin.mini_banking_app.model.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionResponse {

    private Long id;
    private UUID fromAccountId;
    private UUID toAccountId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private TransactionStatus status;
}
