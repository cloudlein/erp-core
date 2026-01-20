package com.learn.erp_core.auth.domain.repository;

import com.learn.erp_core.auth.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Optional<User> findByUserId(Long userId);

    Page<User> findAllUser(String search, Pageable pageable);

}

