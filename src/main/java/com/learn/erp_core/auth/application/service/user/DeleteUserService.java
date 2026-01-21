package com.learn.erp_core.auth.application.service.user;

import com.learn.erp_core.auth.application.port.in.user.DeleteUserUseCase;
import com.learn.erp_core.auth.domain.model.User;
import com.learn.erp_core.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id : " + userId));

        user.deactivate();

        userRepository.save(user);
    }
}
