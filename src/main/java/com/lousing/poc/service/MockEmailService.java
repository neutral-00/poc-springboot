package com.lousing.poc.service;

public class MockEmailService implements NotificationService{
    @Override
    public void send(String message, String recipient) {
        System.out.println("🧪 MOCK Email to " + recipient + ": " + message);
    }
}
