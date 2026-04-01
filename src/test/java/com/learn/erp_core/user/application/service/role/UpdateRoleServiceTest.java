package com.learn.erp_core.user.application.service.role;

import com.learn.erp_core.shared.exception.ConflictException;
import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.adapter.out.persistence.mapper.RoleMapper;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.application.dto.role.UpdateRoleRequest;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateRoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private UpdateRoleService updateRoleService;

    @Test
    @DisplayName("Should update role successfully when data is valid")
    void updateRole_ShouldReturnUpdatedRole_WhenDataIsValid()
    {
        Long roleId = 1L;
        String oldName = "testrole";
        String newName = "testrole2";

        UpdateRoleRequest request = UpdateRoleRequest.builder()
                .name(newName)
                .build();

        Permission permission = Permission.builder()
                .name("testPermission")
                .description("testPermissionDescription")
                .build();

        Role existingRole = Role.builder()
                .id(roleId)
                .name(oldName)
                .description("testroledesc")
                .permissions(Set.of(permission))
                .build();

        Role updatedRole = existingRole.toBuilder()
                .name(newName)
                .build();

        RoleResponse expectedResponse = RoleResponse.builder()
                .id(roleId)
                .name(newName)
                .description("testroledesc")
                .build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));
        when(roleRepository.existsByName(newName)).thenReturn(false);

        when(roleRepository.save(any(Role.class))).thenReturn(updatedRole);

        when(roleMapper.toResponse(any(Role.class))).thenReturn(expectedResponse);

        RoleResponse actualResponse = updateRoleService.updateRole(roleId, request);

        assertNotNull(actualResponse);
        assertEquals(newName, actualResponse.getName());

        verify(roleRepository).findById(roleId);
        verify(roleRepository).existsByName(newName);

        ArgumentCaptor<Role> mapperCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleMapper).toResponse(mapperCaptor.capture());

        Role rolePassedToMapper = mapperCaptor.getValue();
        assertEquals(newName, rolePassedToMapper.getName());

    }

    @Test
    @DisplayName("Should throw REsourceNotFoundException when role is not found")
    void updateRole_ShouldThrowException_WhenRoleIsNotFound(){
        Long roleId = 1L;
        UpdateRoleRequest request = UpdateRoleRequest.builder()
                .build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            updateRoleService.updateRole(roleId, request);
        });

        verify(roleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw ConflictException when new name is already use by another role")
    void updateRole_ShouldThrowConflictException_WhenNewNameIsAlreadyUsedByAnotherRole(){
        Long roleId = 1L;
        String oldName = "testrole";
        String newName = "testrole2";

        UpdateRoleRequest request = UpdateRoleRequest.builder()
                .name(newName)
                .build();

        Role existingRole = Role.builder()
                .id(roleId)
                .name(oldName)
                .build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));

        when(roleRepository.existsByName(newName)).thenReturn(true);

        assertThrows(ConflictException.class, () -> {
            updateRoleService.updateRole(roleId, request);
        });

        verify(roleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not update name when request name is same as old name")
    void updateRole_ShouldNotUpdateName_WhenRequestNameIsSameAsOldName(){
        Long roleId = 1L;
        String name = "sameRole";


        UpdateRoleRequest request = UpdateRoleRequest.builder()
                .name(name)
                .build();

        Role existringRole = Role.builder()
                .id(roleId)
                .name(name)
                .build();

        Role updatedRole = existringRole.toBuilder()
                .name(name)
                .build();

        RoleResponse expectedResponse = RoleResponse.builder().build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(existringRole));
        when(roleRepository.save(any(Role.class))).thenReturn(updatedRole);
        when(roleMapper.toResponse(any(Role.class))).thenReturn(expectedResponse);

        updateRoleService.updateRole(roleId, request);

        verify(roleRepository, never()).existsByName(anyString());
        verify(roleRepository).save(any(Role.class));
    }
}
