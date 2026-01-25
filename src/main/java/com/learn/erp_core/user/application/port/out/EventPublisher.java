package com.learn.erp_core.user.application.port.out;

import com.learn.erp_core.user.domain.event.UserRegisteredEvent;

public interface EventPublisher {
    void publish(UserRegisteredEvent event);
}
