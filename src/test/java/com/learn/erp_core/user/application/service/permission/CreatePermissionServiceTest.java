package com.learn.erp_core.user.application.service.permission;

import com.learn.erp_core.shared.exception.ConflictException;
import com.learn.erp_core.user.adapter.out.persistence.mapper.PermissionMapper;
import com.learn.erp_core.user.application.dto.permission.CreatePermissionRequest;
import com.learn.erp_core.user.application.dto.permission.PermissionResponse;
import com.learn.erp_core.user.application.dto.user.CreateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreatePermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionMapper permissionMapper;


    @InjectMocks
    private CreatePermissionService createPermissionService;

    @Test
    void createPermission_ShouldReturnPermissionResponse_WhenDataIsValid() {
        CreatePermissionRequest request = CreatePermissionRequest.builder()
                .name("permissiontest")
                .description("permissiontest")
                .build();

        Permission permission = Permission.builder()
                .name("permissiontest")
                .description("permissiontest")
                .build();

        Permission savedPermission = Permission.builder()
                .id(1L)
                .name("permissiontest")
                .description("permissiontest")
                .build();

        PermissionResponse expectedResponse = PermissionResponse.builder()
                .id(1L)
                .name("permissiontest")
                .description("permissiontest")
                .build();

        when(permissionRepository.existsByName(request.getName())).thenReturn(false);
        when(permissionMapper.toDomain(request)).thenReturn(permission);
        when(permissionRepository.save(permission)).thenReturn(savedPermission);
        when(permissionMapper.toResponse(savedPermission)).thenReturn(expectedResponse);


        PermissionResponse actualResponse = createPermissionService.createPermission(request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());

        verify(permissionRepository).existsByName(request.getName());
        verify(permissionRepository).save(permission);
    }

    @Test
    void createPermission_ShouldThrowConflictException_WhenNameExists() {

        CreatePermissionRequest request = CreatePermissionRequest.builder()
                .name("existingpermission")
                .description("existingpermissiondesc")
                .build();

        when(permissionRepository.existsByName(request.getName())).thenReturn(true);

        assertThrows(ConflictException.class, () -> createPermissionService.createPermission(request));

        verify(permissionRepository, never()).save(any());
    }
}
