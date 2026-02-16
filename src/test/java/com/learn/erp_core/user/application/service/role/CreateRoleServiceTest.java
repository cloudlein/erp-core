package com.learn.erp_core.user.application.service.role;

import com.learn.erp_core.shared.exception.ConflictException;
import com.learn.erp_core.user.adapter.out.persistence.mapper.RoleMapper;
import com.learn.erp_core.user.application.dto.role.CreateRoleRequest;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private CreateRoleService createRoleService;

    @Test
    @DisplayName("Should create role and return response when data is valid")
    void createRole_ShouldReturnRoleResponse_WhenDataIsValid() {
        // Arrange
        String roleName = "testingrole";
        CreateRoleRequest request = CreateRoleRequest.builder()
                .name(roleName)
                .description("testingroledesc")
                .permission(Set.of("testpermission")) // Request uses Set<String>
                .build();

        // Kita perlu membuat object Permission karena Role domain membutuhkan Set<Permission>
        Permission permission = Permission.builder()
                .id(1L)
                .name("testpermission")
                .description("Test Permission")
                .build();

        Role role = Role.builder()
                .id(1L)
                .name(roleName)
                .description("testingroledesc")
                .permissions(Set.of(permission)) // Role domain uses Set<Permission>
                .build();

        RoleResponse roleResponse = RoleResponse.builder()
                .id(1L)
                .name(roleName)
                .description("testingroledesc")
                .build();

        // Mocking behavior
        when(roleRepository.existsByName(roleName)).thenReturn(false);
        when(roleMapper.toDomain(request)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toResponse(role)).thenReturn(roleResponse);

        // Act
        RoleResponse result = createRoleService.createRole(request);

        // Assert
        assertNotNull(result);
        assertEquals(roleName, result.getName());
        
        verify(roleRepository).existsByName(roleName);
        verify(roleMapper).toDomain(request);
        verify(roleRepository).save(role);
        verify(roleMapper).toResponse(role);
    }

    @Test
    @DisplayName("Should throw ConflictException when role name already exists")
    void createRole_ShouldThrowConflictException_WhenRoleExists() {
        // Arrange
        String roleName = "existingRole";
        CreateRoleRequest request = CreateRoleRequest.builder()
                .name(roleName)
                .build();

        when(roleRepository.existsByName(roleName)).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> createRoleService.createRole(request));
        
        verify(roleRepository).existsByName(roleName);
        verify(roleRepository, never()).save(any());
    }
}
