package com.lousing.poc.service;

public class RealEmailService implements NotificationService{
    @Override
    public void send(String message, String recipient) {
        System.out.println("📧 REAL Email API → " + recipient + ": " + message);
    }
}
