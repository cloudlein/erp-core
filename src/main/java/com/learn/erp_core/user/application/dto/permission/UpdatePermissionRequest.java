package com.learn.erp_core.user.application.dto.permission;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePermissionRequest {

    @Size(min = 4, max = 100, message = "Permission length must be between 4 and 100")
    private String name;

    private String description;

}
