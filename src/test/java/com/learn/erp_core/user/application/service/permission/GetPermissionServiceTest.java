package com.learn.erp_core.user.application.service.permission;

import com.learn.erp_core.user.adapter.out.persistence.mapper.PermissionMapper;
import com.learn.erp_core.user.application.dto.permission.PermissionResponse;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.PermissionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetPermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionMapper permissionMapper;

    @InjectMocks
    private GetPermissionService getPermissionService;

    @Test
    @DisplayName("Should return PermissionResponse when permission is found by ID")
    void getPermissionById_ShouldReturnUser_WhenPermissionExists() {
        // Arrange
        Long permissionId = 1L;
        Permission permission = Permission.builder()
                .id(permissionId)
                .name("permissiontestuser")
                .description("permissiontestuserdesc")
                .build();

        PermissionResponse expectedResponse = PermissionResponse.builder()
                .id(permissionId)
                .name("permissiontestuser")
                .description("permissiontestuserdesc")
                .build();

        when(permissionRepository.findByPermissionId(permissionId)).thenReturn(Optional.of(permission));
        when(permissionMapper.toResponse(permission)).thenReturn(expectedResponse);

        // Act
        PermissionResponse actualResponse = getPermissionService.getPermissionById(permissionId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());

        verify(permissionRepository).findByPermissionId(permissionId);
        verify(permissionMapper).toResponse(permission);
    }
}
