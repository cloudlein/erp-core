package com.learn.erp_core.auth.application.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private Boolean isActive;
    private Set<String> roles;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

}
