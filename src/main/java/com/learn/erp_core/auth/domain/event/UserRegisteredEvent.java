package com.learn.erp_core.auth.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserRegisteredEvent {
    private final Long userId;
    private final String email;
    private final LocalDateTime occurredOn;
}

