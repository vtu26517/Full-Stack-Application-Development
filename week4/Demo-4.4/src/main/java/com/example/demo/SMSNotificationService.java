package com.example.demo;
import org.springframework.stereotype.Service;

@Service("smsService")

public class SMSNotificationService implements NotificationService {

    public String sendNotification() {
        return "SMS notification sent!";
    }
}