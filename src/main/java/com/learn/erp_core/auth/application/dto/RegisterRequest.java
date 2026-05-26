package com.learn.erp_core.auth.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username length must be between 4 and 50")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 12, max = 72, message = "Password length must be at least 12 characters")
    private String password;

}
