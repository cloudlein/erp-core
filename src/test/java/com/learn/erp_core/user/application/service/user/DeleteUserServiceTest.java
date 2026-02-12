package com.learn.erp_core.user.application.service.user;

import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserService deleteUserService;

    @Test
    void deleteUser_ShouldDeleteUser_WhenIdExists() {
        Long userId = 1L;
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        deleteUserService.deleteUser(userId);
        verify(userRepository).delete(userId);
    }

    @Test
    void deleteUser_ShouldThrowResourceNotFound_WhenUserDoesNotExist() {
        Long userId = 99L;
        when(userRepository.existsByUserId(userId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> deleteUserService.deleteUser(userId));
        verify(userRepository, never()).delete(userId);
    }
}
