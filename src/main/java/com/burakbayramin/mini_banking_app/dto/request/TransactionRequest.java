package com.burakbayramin.mini_banking_app.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request payload for performing a transaction between two accounts.")
public class TransactionRequest {

    @NotNull(message = "From account ID cannot be null")
    @Schema(
            description = "UUID of the account from which the amount will be debited.",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            required = true
    )
    private UUID fromAccountId;

    @NotNull(message = "To account ID cannot be null")
    @Schema(
            description = "UUID of the account to which the amount will be credited.",
            example = "6b1e8b3e-9c4d-4c3a-8f2e-7d1a6e2b5c3f",
            required = true
    )
    private UUID toAccountId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Amount must be greater than zero")
    @Schema(
            description = "The amount to be transferred. Must be greater than zero.",
            example = "150.75",
            required = true
    )
    private BigDecimal amount;
}
