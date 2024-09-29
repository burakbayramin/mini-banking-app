package com.burakbayramin.mini_banking_app.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request payload for updating an existing account's details.")
public class AccountUpdateRequest {

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
    @Schema(
            description = "Name of the account holder. Must be between 2 and 25 characters.",
            example = "Jane Doe",
            required = true
    )
    private String name;
}
