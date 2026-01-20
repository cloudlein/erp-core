package com.learn.erp_core.auth.application.service.user;

import com.learn.erp_core.auth.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.auth.application.dto.user.CreateUserRequest;
import com.learn.erp_core.auth.application.dto.user.UserResponse;
import com.learn.erp_core.auth.application.port.in.user.CreateUserUseCase;
import com.learn.erp_core.auth.application.port.out.EventPublisher;
import com.learn.erp_core.auth.domain.event.UserRegisteredEvent;
import com.learn.erp_core.auth.domain.model.Role;
import com.learn.erp_core.auth.domain.model.User;
import com.learn.erp_core.auth.domain.repository.RoleRepository;
import com.learn.erp_core.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CreateUserService implements CreateUserUseCase {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final EventPublisher eventPublisher;

    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("email already exists");
        }

        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null) {
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
                roles.add(role);
            }
        }

        User domain = userMapper.toDomain(request).toBuilder()
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .roles(roles)
                .build();

        User savedUser = userRepository.save(domain);

        // Publish Domain Event
        eventPublisher.publish(new UserRegisteredEvent(savedUser.getId(), savedUser.getEmail(), LocalDateTime.now()));

        return userMapper.toResponse(savedUser);
    }
}
