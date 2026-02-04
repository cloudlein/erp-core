package com.learn.erp_core.user.application.dto.role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class CreateRoleRequest {

    @NotBlank(message = "name is required")
    @Size(min = 4, max = 50, message = "Role length must be between 4 and 50")
    private String name;

    private String description;

    private Set<String> Permission;
}
