package com.burakbayramin.mini_banking_app.dto.request;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AccountUpdateRequest {

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
    private String name;
}
