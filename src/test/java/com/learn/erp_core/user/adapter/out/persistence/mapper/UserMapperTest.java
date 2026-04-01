package com.learn.erp_core.user.adapter.out.persistence.mapper;

import com.learn.erp_core.user.adapter.out.persistence.entity.UserEntity;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    // Kita buat anonymous class untuk mengetes default method di interface
    private final UserMapper userMapper = new UserMapper() {
        @Override
        public User toDomain(com.learn.erp_core.user.adapter.out.persistence.entity.UserEntity entity) {
            return null;
        }

        @Override
        public User toDomain(com.learn.erp_core.user.application.dto.user.CreateUserRequest request) {
            return null;
        }

        @Override
        public UserEntity toEntity(com.learn.erp_core.user.domain.model.User domain) {
            return null;
        }

        @Override
        public UserResponse toResponse(com.learn.erp_core.user.domain.model.User user) {
            return null;
        }
    };

    @Test
    @DisplayName("Should convert Set<String> to Set<Role> correctly")
    void mapRolesFromString_ShouldConvertCorrectly() {
        // Arrange
        Set<String> roleNames = Set.of("ADMIN", "USER");

        // Act
        Set<Role> roles = userMapper.mapRolesFromString(roleNames);

        // Assert
        assertNotNull(roles);
        assertEquals(2, roles.size());
        assertTrue(roles.stream().anyMatch(r -> r.getName().equals("ADMIN")));
        assertTrue(roles.stream().anyMatch(r -> r.getName().equals("USER")));
    }

    @Test
    @DisplayName("Should return null when input is null")
    void mapRolesFromString_ShouldReturnNull_WhenInputIsNull() {
        // Act
        Set<Role> roles = userMapper.mapRolesFromString(null);

        // Assert
        assertNull(roles);
    }
    
    @Test
    @DisplayName("Should return empty set when input is empty")
    void mapRolesFromString_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Act
        Set<Role> roles = userMapper.mapRolesFromString(Collections.emptySet());

        // Assert
        assertNotNull(roles);
        assertTrue(roles.isEmpty());
    }
}
