package com.learn.erp_core.user.application.dto.permission;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePermissionRequest {

    @NotBlank(message = "name is required")
    private String name;

    private String description;

}
