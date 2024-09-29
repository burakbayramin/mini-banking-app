package com.burakbayramin.mini_banking_app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request payload for creating a new account")
public class AccountCreateRequest {

    @NotNull(message = "Number cannot be null")
    @Pattern(regexp = "^[0-9]+$", message = "Number must be numeric")
    @Schema(
            description = "Account number consisting of digits only.",
            example = "1234567890",
            required = true
    )
    private String number;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
    @Schema(
            description = "Name of the account holder. Must be between 2 and 25 characters.",
            example = "John Doe",
            required = true
    )
    private String name;
}
