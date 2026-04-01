package com.learn.erp_core.user.adapter.out.persistence.mapper;

import com.learn.erp_core.user.adapter.out.persistence.entity.RoleEntity;
import com.learn.erp_core.user.application.dto.role.CreateRoleRequest;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {
    Role toDomain(RoleEntity entity);
    RoleEntity toEntity(Role domain);

    Role toDomain(CreateRoleRequest request);

    RoleResponse toResponse(Role role);

    @Named("mapRoleNames")
    default Set<String> mapRoleNames(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
