package com.learn.erp_core.auth.application.port.in.user;

import com.learn.erp_core.auth.application.dto.user.CreateUserRequest;
import com.learn.erp_core.auth.application.dto.user.UserResponse;

public interface CreateUserUseCase {
    UserResponse createUser(CreateUserRequest request);
}
