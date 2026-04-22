package com.learn.erp_core.auth.application.port.in;

import com.learn.erp_core.auth.application.dto.AuthResponse;
import com.learn.erp_core.auth.application.dto.RegisterRequest;

public interface RegisterUseCase {

    AuthResponse register(RegisterRequest request);

}
