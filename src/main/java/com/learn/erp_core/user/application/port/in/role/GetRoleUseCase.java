package com.learn.erp_core.user.application.port.in.role;

import com.learn.erp_core.shared.dto.OptionResponse;
import com.learn.erp_core.shared.dto.PaginationResponse;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetRoleUseCase {

    RoleResponse getByRoleId(Long roleId);
    PaginationResponse<RoleResponse> getAllRole(String search, Pageable pageable);
    List<OptionResponse> getOptions();
}
