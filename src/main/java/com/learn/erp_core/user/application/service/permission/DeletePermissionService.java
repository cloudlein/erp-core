package com.learn.erp_core.user.application.service.permission;

import com.learn.erp_core.user.application.port.in.permission.DeletePermissionUseCase;
import com.learn.erp_core.user.domain.repository.PermissionRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeletePermissionService implements DeletePermissionUseCase {

    private final PermissionRepository permissionRepository;


    @Transactional
    @Override
    public void deletePermission(Long permissionId) {
        if (!permissionRepository.existsByPermissionId(permissionId)) {
            throw new IllegalArgumentException("Permission not found with id:" + permissionId);
        }

        permissionRepository.delete(permissionId);
    }
}
