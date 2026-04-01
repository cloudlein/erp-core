package com.learn.erp_core.user.application.service.user;

import com.learn.erp_core.shared.dto.PaginationResponse;
import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private GetUserService getUserService;

    @Test
    @DisplayName("Should return UserResponse when user is found by ID")
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .username("testuser")
                .email("test@example.com")
                .build();
        
        UserResponse expectedResponse = UserResponse.builder()
                .id(userId)
                .username("testuser")
                .email("test@example.com")
                .build();

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(expectedResponse);

        // Act
        UserResponse actualResponse = getUserService.getUserById(userId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getUsername(), actualResponse.getUsername());
        
        verify(userRepository).findByUserId(userId);
        verify(userMapper).toResponse(user);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user is not found by ID")
    void getUserById_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        Long userId = 99L;
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            getUserService.getUserById(userId);
        });

        verify(userRepository).findByUserId(userId);
        verify(userMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("Should return PaginationResponse containing user list when search is successful")
    void getAllUser_ShouldReturnPaginationResponse_WhenUsersExist() {
        // Arrange
        String search = "test";
        Pageable pageable = PageRequest.of(0, 10);
        
        User user1 = User.builder().id(1L).username("user1").build();
        User user2 = User.builder().id(2L).username("user2").build();
        List<User> userList = List.of(user1, user2);
        
        Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());
        
        UserResponse response1 = UserResponse.builder().id(1L).username("user1").build();
        UserResponse response2 = UserResponse.builder().id(2L).username("user2").build();

        when(userRepository.findAllUser(search, pageable)).thenReturn(userPage);
        when(userMapper.toResponse(user1)).thenReturn(response1);
        when(userMapper.toResponse(user2)).thenReturn(response2);

        // Act
        PaginationResponse<UserResponse> result = getUserService.getAllUser(search, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("user1", result.getContent().get(0).getUsername());
        
        verify(userRepository).findAllUser(search, pageable);
        verify(userMapper, times(2)).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Should return empty PaginationResponse when no users found")
    void getAllUser_ShouldReturnEmptyPagination_WhenNoUsersFound() {
        // Arrange
        String search = "nonexistent";
        Pageable pageable = PageRequest.of(0, 10);
        
        Page<User> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(userRepository.findAllUser(search, pageable)).thenReturn(emptyPage);

        // Act
        PaginationResponse<UserResponse> result = getUserService.getAllUser(search, pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        
        verify(userRepository).findAllUser(search, pageable);
        verify(userMapper, never()).toResponse(any());
    }
}
