package com.learn.erp_core.user.application.port.in.role;

import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.application.dto.role.UpdateRoleRequest;

public interface UpdateRoleUseCase {

    RoleResponse updateRole(Long roleId, UpdateRoleRequest request);

}
