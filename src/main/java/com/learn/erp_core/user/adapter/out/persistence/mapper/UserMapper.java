package com.learn.erp_core.user.adapter.out.persistence.mapper;

import com.learn.erp_core.user.adapter.out.persistence.entity.UserEntity;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    User toDomain(UserEntity entity);
    UserEntity toEntity(User domain);
    
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoleNames")
    UserResponse toResponse(User domain);
}
