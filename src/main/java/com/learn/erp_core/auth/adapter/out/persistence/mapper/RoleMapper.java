package com.learn.erp_core.auth.adapter.out.persistence.mapper;

import com.learn.erp_core.auth.adapter.out.persistence.entity.RoleEntity;
import com.learn.erp_core.auth.application.dto.role.CreateRoleRequest;
import com.learn.erp_core.auth.application.dto.role.RoleResponse;
import com.learn.erp_core.auth.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    RoleEntity toEntity(Role role);

    Role toDomain(RoleEntity role);

    RoleResponse toResponse(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Role toDomain(CreateRoleRequest request);
}

