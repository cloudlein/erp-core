package com.learn.erp_core.user.domain.repository;

import com.learn.erp_core.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Page<User> findAllUser(String search, Pageable pageable);
    void delete(Long userId);
    Boolean existsByUserId(Long userId);
}
