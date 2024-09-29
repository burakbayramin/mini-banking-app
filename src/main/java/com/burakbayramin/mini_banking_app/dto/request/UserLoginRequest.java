package com.burakbayramin.mini_banking_app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request payload for user login.")
public class UserLoginRequest {

    @NotBlank(message = "Username is required")
    @Schema(
            description = "Username of the user attempting to log in.",
            example = "johndoe",
            required = true
    )
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(
            description = "Password of the user attempting to log in.",
            example = "P@ssw0rd!",
            required = true
    )
    private String password;
}
