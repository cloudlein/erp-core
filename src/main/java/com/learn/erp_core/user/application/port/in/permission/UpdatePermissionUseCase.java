package com.learn.erp_core.user.application.port.in.permission;

import com.learn.erp_core.user.application.dto.permission.PermissionResponse;
import com.learn.erp_core.user.application.dto.permission.UpdatePermissionRequest;
import com.learn.erp_core.user.application.dto.role.RoleResponse;

public interface UpdatePermissionUseCase {

    PermissionResponse updatePermission(Long permissionId, UpdatePermissionRequest request);

}
