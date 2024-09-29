package com.burakbayramin.mini_banking_app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request payload for searching accounts based on account number and/or account holder name.")
public class AccountSearchRequest {

    @NotNull
    @Schema(
            description = "Account number to search for.",
            example = "1234567890"
    )
    private String number;

    @NotNull
    @Schema(
            description = "Name of the account holder to search for.",
            example = "John Doe"
    )
    private String name;
}
