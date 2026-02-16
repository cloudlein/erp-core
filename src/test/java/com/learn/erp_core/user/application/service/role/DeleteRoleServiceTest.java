package com.learn.erp_core.user.application.service.role;

import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteRoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private DeleteRoleService deleteRoleService;

    @Test
    @DisplayName("Should delete role when ID exists")
    void deleteRole_ShouldDeleteRole_WhenIdExists(){
        Long roleId = 1L;
        when(roleRepository.existsById(roleId)).thenReturn(true);
        deleteRoleService.deleteRole(roleId);
        verify(roleRepository).delete(roleId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when role does not exist")
    void deleteRole_ShouldThrowResourceNotFoundException_WhenRoleDoesNotExist(){
        Long roleId = 1L;
        when(roleRepository.existsById(roleId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> deleteRoleService.deleteRole(roleId));
        verify(roleRepository, never()).delete(roleId);
    }
}
