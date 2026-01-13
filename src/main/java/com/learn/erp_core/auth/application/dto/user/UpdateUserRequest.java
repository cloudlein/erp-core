package com.learn.erp_core.auth.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class UpdateUserRequest {

    @Size(min = 5 ,max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Email(message = "Only format email can be allowed")
    private String email;

    private Set<String> roles;

}
