package com.ubb.notificationsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/test")

public class TestController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/{id}")
    public void sendTestMessage(@PathVariable final Long id, @RequestBody final String message) {
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(id), "/message", message);
    }
}
