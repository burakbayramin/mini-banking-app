package com.burakbayramin.mini_banking_app.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AccountCreateRequest {

    @NotNull(message = "Number cannot be null")
    @Pattern(regexp = "^[0-9]+$", message = "Number must be numeric")
    private String number;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
    private String name;

//    @NotNull
//    private BigDecimal initialBalance;
}
