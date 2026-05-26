package com.learn.erp_core.auth.application.service;

import com.learn.erp_core.auth.adapter.out.security.CustomUserDetails;
import com.learn.erp_core.auth.adapter.out.security.JwtService;
import com.learn.erp_core.auth.application.dto.AuthResponse;
import com.learn.erp_core.auth.application.dto.RegisterRequest;
import com.learn.erp_core.auth.application.port.in.RegisterUseCase;
import com.learn.erp_core.shared.exception.ConflictException;
import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import com.learn.erp_core.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class RegisterService implements RegisterUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Registration attempt failed: username {} already exists", request.getUsername());
            throw new ConflictException("Username already exists");
        }

        // Fetch default ROLE_USER role
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role ROLE_USER not found"));


        User user = User.builder()
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .isActive(true)
                .roles(Set.of(defaultRole))
                .build();

        User savedUser = userRepository.save(user);
        CustomUserDetails userDetails = new CustomUserDetails(savedUser);

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .username(savedUser.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
