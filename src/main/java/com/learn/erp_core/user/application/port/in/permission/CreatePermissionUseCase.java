package com.learn.erp_core.user.application.port.in.permission;

import com.learn.erp_core.user.application.dto.permission.CreatePermissionRequest;
import com.learn.erp_core.user.application.dto.permission.PermissionResponse;

public interface CreatePermissionUseCase {

    PermissionResponse createPermission(CreatePermissionRequest request);

}

