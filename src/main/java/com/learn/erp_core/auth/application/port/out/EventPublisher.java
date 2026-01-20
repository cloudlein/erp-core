package com.learn.erp_core.auth.application.port.out;

import com.learn.erp_core.auth.domain.event.UserRegisteredEvent;

public interface EventPublisher {
    void publish(UserRegisteredEvent event);
}
