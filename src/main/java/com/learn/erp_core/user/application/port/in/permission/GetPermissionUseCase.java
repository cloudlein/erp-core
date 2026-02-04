package com.learn.erp_core.user.application.port.in.permission;

import com.learn.erp_core.shared.dto.OptionResponse;
import com.learn.erp_core.shared.dto.PaginationResponse;
import com.learn.erp_core.user.application.dto.permission.PermissionResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetPermissionUseCase {

    PermissionResponse getPermissionById(Long permissionId);

    PaginationResponse<PermissionResponse> getAllPermissions(String search , Pageable pageable);

    List<OptionResponse> getOptions();

}
