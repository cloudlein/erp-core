package com.learn.erp_core.user.application.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username length must be between 4 and 50")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(min = 6, max = 100, message = "Email length must be between 6 and 100")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 12, max = 72, message = "Password length must be at least 12 characters")
    private String password;

    @NotEmpty(message = "At least one role is required")
    private Set<String> roles;
}
