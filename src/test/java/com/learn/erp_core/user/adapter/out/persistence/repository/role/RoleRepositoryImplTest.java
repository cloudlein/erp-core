package com.learn.erp_core.user.adapter.out.persistence.repository.role;

import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.adapter.out.persistence.entity.PermissionEntity;
import com.learn.erp_core.user.adapter.out.persistence.entity.RoleEntity;
import com.learn.erp_core.user.adapter.out.persistence.mapper.RoleMapper;
import com.learn.erp_core.user.adapter.out.persistence.repository.permission.JpaPermissionRepository;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleRepositoryImplTest {

    @Mock
    private JpaRoleRepository jpaRoleRepository;

    @Mock
    private JpaPermissionRepository jpaPermissionRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleRepositoryImpl roleRepository;

    @Test
    @DisplayName("Should save role and return role domain")
    void save_ShouldSaveRole_WhenPermissionExists() {
        Permission permission = Permission.builder()
                .name("permission")
                .description("description")
                .build();

        Role role = Role.builder()
                .name("role")
                .description("description")
                .permissions(Set.of(permission))
                .build();

        PermissionEntity permissionEntity = PermissionEntity.builder()
                .name("permission")
                .description("description")
                .build();

        RoleEntity roleEntity = RoleEntity.builder()
                .name("role")
                .description("description")
                .build();

        when(roleMapper.toEntity(role)).thenReturn(roleEntity);
        when(jpaPermissionRepository.findByName("permission")).thenReturn(Optional.of(permissionEntity));
        when(jpaRoleRepository.save(roleEntity)).thenReturn(roleEntity);
        when(roleMapper.toDomain(roleEntity)).thenReturn(role);

        Role savedRole = roleRepository.save(role);

        assertNotNull(savedRole);
        verify(jpaPermissionRepository).findByName("permission");
        verify(jpaRoleRepository).save(roleEntity);

    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when save role with non-existent permission")
    void save_ShouldThrowResourceNotFoundException_WhenPermissionDoesNotExist() {
        Permission permission  = Permission.builder()
                .name("permission")
                .description("description")
                .build();

        Role role = Role.builder()
                .name("role")
                .description("description")
                .permissions(Set.of(permission))
                .build();

        RoleEntity roleEntity = new RoleEntity();

        when(roleMapper.toEntity(role)).thenReturn(roleEntity);
        when(jpaPermissionRepository.findByName("permission")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,  () -> roleRepository.save(role));
        verify(jpaRoleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find role by name")
    void find_ShouldFindRole_WhenPermissionExists() {
        String role =  "role";
        RoleEntity roleEntity = new RoleEntity();
        Role domain = Role.builder()
                .name(role)
                .build();

        when(jpaRoleRepository.findByName(role)).thenReturn(Optional.of(roleEntity));
        when(roleMapper.toDomain(roleEntity)).thenReturn(domain);

        Optional<Role> result = roleRepository.findByName(role);

        assertTrue(result.isPresent());
        assertEquals(role, result.get().getName());
    }

    @Test
    @DisplayName("Should find all role with pagination")
    void findAll_ShouldFindAllRole_WhenPermissionExists() {
        Pageable pageable = Pageable.unpaged();
        RoleEntity roleEntity = new RoleEntity();

        Page<RoleEntity> pageEntity = new PageImpl<>(List.of(roleEntity));
        Role domain = new Role();

        when(jpaRoleRepository.findAll(any(Pageable.class))).thenReturn(pageEntity);
        when(roleMapper.toDomain(roleEntity)).thenReturn(domain);

        Page<Role> result = roleRepository.findAllRole(null, pageable);

        assertEquals(1, result.getTotalElements());
        verify(jpaRoleRepository).findAll(pageable);
    }

}
