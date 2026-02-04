package com.learn.erp_core.user.application.service.permission;

import com.learn.erp_core.user.adapter.out.persistence.mapper.PermissionMapper;
import com.learn.erp_core.user.application.dto.permission.PermissionResponse;
import com.learn.erp_core.user.application.dto.permission.UpdatePermissionRequest;
import com.learn.erp_core.user.application.port.in.permission.UpdatePermissionUseCase;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdatePermissionService implements UpdatePermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper  permissionMapper;

    @Transactional
    @Override
    public PermissionResponse updatePermission(Long permissionId, UpdatePermissionRequest request) {

        Permission permission = permissionRepository.findByPermissionId(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found with id : " + permissionId));

        if (permissionRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Permission already exists with name : " + request.getName());
        }

        Permission savedPermission = permissionRepository.save(permission);

        return permissionMapper.toResponse(savedPermission);
    }
}
