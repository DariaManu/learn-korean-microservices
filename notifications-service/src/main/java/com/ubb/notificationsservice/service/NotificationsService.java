package com.ubb.notificationsservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void notifyClientThatUserProgressLevelChanged(final Long userId, final String newProgressLevel) {
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(userId), "/progress-level-changed", newProgressLevel);
    }
}
