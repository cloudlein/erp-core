package com.learn.erp_core.auth.application.service.user;

import com.learn.erp_core.auth.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.auth.application.dto.user.UpdateUserRequest;
import com.learn.erp_core.auth.application.dto.user.UserResponse;
import com.learn.erp_core.auth.application.port.in.user.UpdateUserUseCase;
import com.learn.erp_core.auth.domain.model.Role;
import com.learn.erp_core.auth.domain.model.User;
import com.learn.erp_core.auth.domain.repository.RoleRepository;
import com.learn.erp_core.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User existingUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id : " + userId));

        if (
                request.getUsername() != null &&
                        !request.getUsername().equals(existingUser.getUsername()) &&
                        userRepository.existsByUsername(request.getUsername())
        ) {
            throw new IllegalArgumentException("Username already exists");
        }


        if (
                request.getEmail() != null &&
                        !request.getEmail().equals(existingUser.getEmail()) &&
                        userRepository.existsByEmail(request.getEmail())
        ) {
            throw new IllegalArgumentException("Email already exists");
        }

        Set<Role> rolesToSet = existingUser.getRoles();

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> newRoles = new HashSet<>();
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Role not found: " + roleName)
                        );
                newRoles.add(role);
            }
            rolesToSet = newRoles;
        }


        User user = existingUser.toBuilder()
                .username(request.getUsername() != null ? request.getUsername() : existingUser.getUsername())
                .email(request.getEmail() != null ? request.getEmail() : existingUser.getEmail())
                .roles(rolesToSet)
                .build();

        User userSaved = userRepository.save(user);
        return userMapper.toResponse(userSaved);
    }
}
