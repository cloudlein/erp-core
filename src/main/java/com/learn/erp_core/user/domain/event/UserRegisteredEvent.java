package com.learn.erp_core.user.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisteredEvent {
    private Long userId;
    private String email;
}
