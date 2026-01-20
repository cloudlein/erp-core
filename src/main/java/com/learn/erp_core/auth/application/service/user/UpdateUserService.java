package com.learn.erp_core.auth.application.service.user;

import com.learn.erp_core.auth.application.dto.user.UpdateUserRequest;
import com.learn.erp_core.auth.application.dto.user.UserResponse;
import com.learn.erp_core.auth.application.port.in.user.UpdateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateUserService implements UpdateUserUseCase {
    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        // TODO: Implement Logic
        return null;
    }
}
