package com.burakbayramin.mini_banking_app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request payload for user registration.")
public class UserRegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @Schema(
            description = "Username of the new user. Must be between 4 and 50 characters.",
            example = "johndoe123",
            required = true
    )
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    @Schema(
            description = "Password for the new user account. Must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character.",
            example = "P@ssw0rd!",
            required = true
    )
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(
            description = "Email address of the new user. Must be a valid email format.",
            example = "johndoe@example.com",
            required = true
    )
    private String email;
}
