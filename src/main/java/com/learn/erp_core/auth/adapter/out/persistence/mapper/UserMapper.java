package com.learn.erp_core.auth.adapter.out.persistence.mapper;

import com.learn.erp_core.auth.adapter.out.persistence.entity.UserEntity;
import com.learn.erp_core.auth.application.dto.user.CreateUserRequest;
import com.learn.erp_core.auth.application.dto.user.UserResponse;
import com.learn.erp_core.auth.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserEntity toEntity(User user);

    User toDomain(UserEntity user);

    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toDomain(CreateUserRequest request);

}

