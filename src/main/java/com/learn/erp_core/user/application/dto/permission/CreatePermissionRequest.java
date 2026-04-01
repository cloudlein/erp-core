package com.learn.erp_core.user.application.dto.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 4, max = 100, message = "Permission length must be between 4 and 100")
    private String name;

    private String description;

}
