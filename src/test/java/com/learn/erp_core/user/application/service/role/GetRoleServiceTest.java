package com.learn.erp_core.user.application.service.role;

import com.learn.erp_core.shared.dto.PaginationResponse;
import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.adapter.out.persistence.mapper.RoleMapper;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetRoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private GetRoleService getRoleService;

    @Test
    @DisplayName("Should return RoleResponse when role is found by ID")
    void getRoleById_ShouldReturnRole_WhenRoleExists() {

        Long roleId = 1L;

        Permission permission = Permission.builder()
                .id(roleId)
                .name("testpermission")
                .description("Test Permission")
                .build();

        Role role = Role.builder()
                .id(roleId)
                .name("roletesting")
                .description("roletestingdesc")
                .permissions(Set.of(permission))
                .build();

        RoleResponse exepectedRoleResponse = RoleResponse.builder()
                .id(roleId)
                .name("roletesting")
                .name("roletesting")
                .description("roletestingdesc")
                .build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(roleMapper.toResponse(role)).thenReturn(exepectedRoleResponse);

        RoleResponse actualResponse = getRoleService.getByRoleId(roleId);

        assertNotNull(actualResponse);
        assertEquals(exepectedRoleResponse.getId(), actualResponse.getId());

        verify(roleRepository).findById(roleId);
        verify(roleMapper).toResponse(role);

    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when role is not found by IO")
    void getRoleById_ShouldThrowResourceNotFoundException_WhenRoleIsNotFound() {
        Long roleId = 1L;
        when (roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            getRoleService.getByRoleId(roleId);
        });

        verify(roleRepository).findById(roleId);
        verify(roleMapper, never()).toResponse(any());
    }


    @Test
    @DisplayName("Should return PaginationResponse containing role list when search is successful")
    void getAllRole_ShouldReturnPaginationResponse_WhenSearchISuccessful() {

        String search = "test";
        Pageable pageable = PageRequest.of(0,10);

        Permission permission = Permission.builder()
                .id(1L)
                .name("testpermission")
                .description("Test Permission")
                .build();

        Role role1 = Role.builder()
                .id(1L)
                .name("roletesting")
                .description("roletestingdesc")
                .permissions(Set.of(permission))
                .build();


        Role role2 = Role.builder()
                .id(2L)
                .name("roletesting1")
                .description("roletestingdesc1")
                .permissions(Set.of(permission))
                .build();

        List<Role> roleList = List.of(role1, role2);

        Page<Role> rolePage = new PageImpl<>(roleList, pageable, roleList.size());

        RoleResponse response1 = RoleResponse.builder()
                .id(1L)
                .name("roletesting")
                .description("roletestingdesc")
                .build();

        RoleResponse response2 = RoleResponse.builder()
                .id(2L)
                .name("roletesting1")
                .description("roletestingdesc1")
                .build();

        when(roleRepository.findAllRole(search, pageable)).thenReturn(rolePage);
        when(roleMapper.toResponse(role1)).thenReturn(response1);
        when(roleMapper.toResponse(role2)).thenReturn(response2);

        PaginationResponse<RoleResponse> result = getRoleService.getAllRole(search, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("roletesting", result.getContent().get(0).getName());

        verify(roleRepository).findAllRole(search, pageable);
        verify(roleMapper, times(2)).toResponse(any(Role.class));
    }

    @Test
    @DisplayName("Should return empty PaginationResponse when no roles found")
    void getAllRole_ShouldReturnEmptyPaginationResponse_WhenNoRolesFound() {
        String search = "nonexistent";
        Pageable pageable = PageRequest.of(0, 10);

        Page<Role> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when((roleRepository).findAllRole(search, pageable)).thenReturn(emptyPage);

        PaginationResponse<RoleResponse> result = getRoleService.getAllRole(search, pageable);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(roleRepository).findAllRole(search, pageable);
        verify(roleMapper, never()).toResponse(any());

    }

}
