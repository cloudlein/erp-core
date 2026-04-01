package com.learn.erp_core.user.application.port.in.role;

import com.learn.erp_core.user.application.dto.role.CreateRoleRequest;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.application.dto.user.UserResponse;

public interface CreateRoleUseCase {

    RoleResponse createRole(CreateRoleRequest request);

}
