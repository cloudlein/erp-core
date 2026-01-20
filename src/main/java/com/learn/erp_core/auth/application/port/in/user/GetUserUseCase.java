package com.learn.erp_core.auth.application.port.in.user;

import com.learn.erp_core.auth.application.dto.user.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetUserUseCase {
    UserResponse getUserById(Long userId);
    List<UserResponse> getAllUser(String search, Pageable pageable);
}
