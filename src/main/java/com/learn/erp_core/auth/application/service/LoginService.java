package com.learn.erp_core.auth.application.service;

import com.learn.erp_core.auth.adapter.out.security.CustomUserDetails;
import com.learn.erp_core.auth.adapter.out.security.JwtService;
import com.learn.erp_core.auth.application.dto.AuthResponse;
import com.learn.erp_core.auth.application.dto.LoginRequest;
import com.learn.erp_core.auth.application.port.in.LoginUseCase;
import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager  authenticationManager;


    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }
}
