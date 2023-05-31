package com.ubb.notificationsservice.messaging;

import com.ubb.notificationsservice.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventHandler {
    private final NotificationsService notificationsService;

    @RabbitListener(queues = "${userProgressLevelChanged.queue}")
    void handleUserProgressLevelChanged(final UserProgressLevelChangedEvent event) {
        System.out.println("UserProgressLevelChangedEvent received!!!");
        System.out.println(event);
        notificationsService.notifyClientThatUserProgressLevelChanged(event.getLearnerUserId(), event.getNewUserProgressLevel());
    }
}
