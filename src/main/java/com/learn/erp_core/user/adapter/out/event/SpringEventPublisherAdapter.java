package com.learn.erp_core.user.adapter.out.event;

import com.learn.erp_core.user.application.port.out.EventPublisher;
import com.learn.erp_core.user.domain.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisherAdapter implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(UserRegisteredEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
