package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Qualifier;

@RestController
public class NotificationController {
    private final NotificationService notificationService;
  @Autowired
    public NotificationController(
            @Qualifier("emailService") NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notify")
    public String send() {
        return notificationService.sendNotification();
    }
}