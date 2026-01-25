package com.learn.erp_core.user.application.port.in.user;

import com.learn.erp_core.user.application.dto.user.UpdateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;

public interface UpdateUserUseCase {
    UserResponse updateUser(Long userId, UpdateUserRequest request);
}
