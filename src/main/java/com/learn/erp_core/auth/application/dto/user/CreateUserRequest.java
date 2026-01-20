package com.learn.erp_core.auth.application.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class CreateUserRequest {

    @NotBlank(message = "Username cannot be empty")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Email(message = "Only format email can be allowed")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Role cannot be empty")
    @Size(min = 1, message = "A role must have at least 1")
    private Set<String> roles;

    private Boolean isActive;

}
