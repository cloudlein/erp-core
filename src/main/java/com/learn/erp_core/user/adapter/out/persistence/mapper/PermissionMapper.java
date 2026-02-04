package com.learn.erp_core.user.adapter.out.persistence.mapper;

import com.learn.erp_core.user.adapter.out.persistence.entity.PermissionEntity;
import com.learn.erp_core.user.application.dto.permission.CreatePermissionRequest;
import com.learn.erp_core.user.application.dto.permission.PermissionResponse;
import com.learn.erp_core.user.domain.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toDomain(PermissionEntity entity);

    Permission toDomain(CreatePermissionRequest request);

    PermissionEntity toEntity(Permission domain);

    PermissionResponse toResponse(Permission permission);
}
