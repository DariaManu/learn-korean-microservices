package com.ubb.learningprogressservice.messaging;

import com.ubb.learningprogressservice.messaging.event.UserProgressLevelChangedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {
    private RabbitTemplate rabbitTemplate;
    private String userProgressLevelChangedExchange;
    private String userProgressLevelChangedRoutingKey;

    @Autowired
    EventDispatcher(final RabbitTemplate rabbitTemplate,
                    @Value("${userProgressLevelChanged.exchange}") final String userProgressLevelChangedExchange,
                    @Value("${userProgressLevel.changed.key}") final String userProgressLevelChangedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.userProgressLevelChangedExchange = userProgressLevelChangedExchange;
        this.userProgressLevelChangedRoutingKey = userProgressLevelChangedRoutingKey;
    }

    public void send(final UserProgressLevelChangedEvent event) {
        rabbitTemplate.convertAndSend(
                userProgressLevelChangedExchange,
                userProgressLevelChangedRoutingKey,
                event
        );
    }
}
