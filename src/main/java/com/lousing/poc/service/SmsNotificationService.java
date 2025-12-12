package com.lousing.poc.service;

public class SmsNotificationService implements NotificationService{
    @Override
    public void send(String message, String recipient) {
        System.out.println("📱 SMS to " + recipient + ": " + message);
    }
}
