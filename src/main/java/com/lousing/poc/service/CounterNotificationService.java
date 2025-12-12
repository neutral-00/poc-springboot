package com.lousing.poc.service;

public class CounterNotificationService implements NotificationService{
    private static int instanceCount = 0;
    private final int instanceId;

    public CounterNotificationService() {
        this.instanceId = ++instanceCount;
        System.out.println("🔢 Created CounterNotificationService #" + instanceId);
    }

    @Override
    public void send(String message, String recipient) {
        System.out.println("🔢 #" + instanceId + " → " + recipient + ": " + message);
    }

    public int getInstanceId() {
        return instanceId;
    }
}
