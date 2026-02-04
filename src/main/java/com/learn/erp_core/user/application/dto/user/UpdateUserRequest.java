package com.learn.erp_core.user.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @Size(min = 4, max = 50, message = "Username length must be between 4 and 50")
    private String username;

    @Size(min = 12, max = 72, message = "Password length must be at least 12 characters")
    private String password;

    @Email(message = "Email must be valid")
    @Size(min = 6, max = 100, message = "Email length must be between 6 and 100")
    private String email;

    @Size(min = 1, message = "At least one role is required")
    private Set<@NotBlank String> roles;

    private Boolean isActive;
}
