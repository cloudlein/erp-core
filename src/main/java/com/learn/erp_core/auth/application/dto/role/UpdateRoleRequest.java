package com.learn.erp_core.auth.application.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateRoleRequest {

    @Size(min = 4,  message = "Role name at least have 4 characters")
    private String name;

    @Size(min = 10, message = "Description at least have 10 characters")
    private String description;

}
