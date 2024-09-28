package com.burakbayramin.mini_banking_app.dto.request;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionRequest {

    @NotNull(message = "From account ID cannot be null")
    private UUID fromAccountId;

    @NotNull(message = "To account ID cannot be null")
    private UUID toAccountId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;}
