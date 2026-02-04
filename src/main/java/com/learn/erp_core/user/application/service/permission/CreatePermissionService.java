package com.learn.erp_core.user.application.service.permission;

import com.learn.erp_core.user.adapter.out.persistence.mapper.PermissionMapper;
import com.learn.erp_core.user.application.dto.permission.CreatePermissionRequest;
import com.learn.erp_core.user.application.dto.permission.PermissionResponse;
import com.learn.erp_core.user.application.port.in.permission.CreatePermissionUseCase;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.repository.PermissionRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePermissionService implements CreatePermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Transactional
    @Override
    public PermissionResponse createPermission(CreatePermissionRequest request) {

        if (permissionRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Name already exists");
        }

        Permission permission = permissionMapper.toDomain(request);
        Permission savedPermission = permissionRepository.save(permission);

        return permissionMapper.toResponse(savedPermission);
    }


}
