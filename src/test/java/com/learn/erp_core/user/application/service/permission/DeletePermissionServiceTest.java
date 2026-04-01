package com.learn.erp_core.user.application.service.permission;

import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.domain.repository.PermissionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletePermissionServiceTest {

    @Mock
    PermissionRepository permissionRepository;

    @InjectMocks
    DeletePermissionService deletePermissionService;


    @Test
    @DisplayName("Should delete permission when ID exists")
    void deletePermission_ShouldDeletePermission_WhenIdExists() {
        Long permissionId = 1L;
        when(permissionRepository.existsByPermissionId(permissionId)).thenReturn(true);
        deletePermissionService.deletePermission(permissionId);
        verify(permissionRepository).delete(permissionId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when permission does not exist")
    void deletePermission_ShouldThrowResourceNotFound_WhenPermissionDoesNotExist() {
        Long permissionId = 99L;
        when(permissionRepository.existsByPermissionId(permissionId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> deletePermissionService.deletePermission(permissionId));
        verify(permissionRepository, never()).delete(permissionId);
    }
}
