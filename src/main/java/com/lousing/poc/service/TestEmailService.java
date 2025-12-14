package com.lousing.poc.service;

public class TestEmailService implements NotificationService{
    @Override
    public void send(String message, String recipient) {
        System.out.println("📝 TEST Log → " + recipient + ": " + message);
    }
}
