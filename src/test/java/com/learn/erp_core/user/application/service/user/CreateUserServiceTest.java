package com.learn.erp_core.user.application.service.user;

import com.learn.erp_core.shared.exception.ConflictException;
import com.learn.erp_core.user.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.user.application.dto.user.CreateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CreateUserService createUserService;

    @Test
    void createUser_ShouldReturnUserResponse_WhenDataIsValid() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .build();

        User savedUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();
                
        UserResponse expectedResponse = UserResponse.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userMapper.toDomain(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(expectedResponse);


        UserResponse actualResponse = createUserService.createUser(request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getUsername(), actualResponse.getUsername());
        
        verify(userRepository).existsByUsername(request.getUsername());
        verify(userRepository).existsByEmail(request.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_ShouldThrowConflictException_WhenUsernameExists() {

        CreateUserRequest request = CreateUserRequest.builder()
                .username("existinguser")
                .email("test@example.com")
                .build();

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        assertThrows(ConflictException.class, () -> createUserService.createUser(request));

        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ShouldThrowConflictException_WhenEmailExists() {

        CreateUserRequest request = CreateUserRequest.builder()
                .username("newuser")
                .email("existing@example.com")
                .build();

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
        assertThrows(ConflictException.class, () -> createUserService.createUser(request));
        verify(userRepository, never()).save(any());
    }
}
