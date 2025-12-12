package com.lousing.poc.service;

public class SlackNotificationService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("💬 Slack to #" + recipient + ": " + message);
    }
}
