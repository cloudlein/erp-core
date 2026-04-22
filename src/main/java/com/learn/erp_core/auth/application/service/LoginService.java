package com.learn.erp_core.auth.application.service;

import com.learn.erp_core.auth.adapter.out.security.CustomUserDetailsService;
import com.learn.erp_core.auth.adapter.out.security.JwtService;
import com.learn.erp_core.auth.application.dto.AuthResponse;
import com.learn.erp_core.auth.application.dto.LoginRequest;
import com.learn.erp_core.auth.application.port.in.LoginUseCase;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager  authenticationManager;

}
