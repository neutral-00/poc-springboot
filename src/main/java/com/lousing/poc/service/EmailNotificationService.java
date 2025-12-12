package com.lousing.poc.service;

public class EmailNotificationService implements NotificationService{
    @Override
    public void send(String message, String recipient) {
        System.out.println("📧 Email to " + recipient + ": " + message);
    }
}
