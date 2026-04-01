package com.learn.erp_core.user.adapter.out.persistence.repository.user;

import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.adapter.out.persistence.entity.RoleEntity;
import com.learn.erp_core.user.adapter.out.persistence.entity.UserEntity;
import com.learn.erp_core.user.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.user.adapter.out.persistence.repository.role.JpaRoleRepository;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private JpaUserRepository jpaUserRepository;

    @Mock
    private JpaRoleRepository jpaRoleRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    @DisplayName("Should save user and return user domain")
    void save_ShouldSaveUser_WhenRolesExist() {
        // Arrange
        Role role = Role.builder().name("ADMIN").build();
        User user = User.builder()
                .username("test")
                .roles(Set.of(role))
                .build();
        
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ADMIN");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");

        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(jpaRoleRepository.findByName("ADMIN")).thenReturn(Optional.of(roleEntity));
        when(jpaUserRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertNotNull(savedUser);
        verify(jpaRoleRepository).findByName("ADMIN");
        verify(jpaUserRepository).save(userEntity);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when saving user with non-existent role")
    void save_ShouldThrowException_WhenRoleNotFound() {
        // Arrange
        Role role = Role.builder().name("UNKNOWN").build();
        User user = User.builder()
                .username("test")
                .roles(Set.of(role))
                .build();

        UserEntity userEntity = new UserEntity();

        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(jpaRoleRepository.findByName("UNKNOWN")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userRepository.save(user));
        verify(jpaUserRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find user by username")
    void findByUsername_ShouldReturnUser_WhenExists() {
        // Arrange
        String username = "test";
        UserEntity entity = new UserEntity();
        User domain = User.builder().username(username).build();

        when(jpaUserRepository.findByUsername(username)).thenReturn(Optional.of(entity));
        when(userMapper.toDomain(entity)).thenReturn(domain);

        // Act
        Optional<User> result = userRepository.findByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    @DisplayName("Should find all users with pagination")
    void findAllUser_ShouldReturnPage_WhenCalled() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        UserEntity entity = new UserEntity();
        Page<UserEntity> pageEntity = new PageImpl<>(List.of(entity));
        User domain = User.builder().build();

        when(jpaUserRepository.findAll(any(Pageable.class))).thenReturn(pageEntity);
        when(userMapper.toDomain(entity)).thenReturn(domain);

        // Act
        Page<User> result = userRepository.findAllUser(null, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        verify(jpaUserRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should find all users with search criteria")
    void findAllUser_ShouldUseSpecification_WhenSearchProvided() {
        // Arrange
        String search = "test";
        Pageable pageable = Pageable.unpaged();
        UserEntity entity = new UserEntity();
        Page<UserEntity> pageEntity = new PageImpl<>(List.of(entity));
        User domain = User.builder().build();

        // Note: Matcher untuk Specification agak tricky dengan Mockito karena objectnya dibuat di dalam method.
        // Kita gunakan any(Specification.class) untuk simplifikasi.
        when(jpaUserRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageEntity);
        when(userMapper.toDomain(entity)).thenReturn(domain);

        // Act
        Page<User> result = userRepository.findAllUser(search, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        verify(jpaUserRepository).findAll(any(Specification.class), eq(pageable));
    }
}
