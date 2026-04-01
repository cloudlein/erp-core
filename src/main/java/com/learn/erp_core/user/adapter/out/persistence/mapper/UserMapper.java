package com.learn.erp_core.user.adapter.out.persistence.mapper;

import com.learn.erp_core.user.adapter.out.persistence.entity.UserEntity;
import com.learn.erp_core.user.application.dto.user.CreateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    User toDomain(UserEntity entity);

    @Mapping(target = "roles", qualifiedByName = "mapRolesFromString")
    User toDomain(CreateUserRequest request);

    UserEntity toEntity(User domain);
    
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoleNames")
    UserResponse toResponse(User user);

    @Named("mapRolesFromString")
    default Set<Role> mapRolesFromString(Set<String> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(roleName -> Role.builder().name(roleName).build())
                .collect(Collectors.toSet());
    }
}
