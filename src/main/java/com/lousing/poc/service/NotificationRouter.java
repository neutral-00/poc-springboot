package com.lousing.poc.service;

import java.util.List;

public class NotificationRouter {
    private final List<NotificationService> notificationServices;

    public NotificationRouter(List<NotificationService> notificationServices) {
        this.notificationServices = notificationServices;
    }

    public void routeCriticalAlert(String message, String recipient) {
        System.out.println("\n🔄 Routing critical alert to all channels:");
        notificationServices.forEach(service -> service.send(message, recipient));
    }

    public void sendViaEmailOnly(String message, String recipient) {
        notificationServices.stream()
                .filter(service -> service instanceof EmailNotificationService)
                .forEach(service -> service.send(message, recipient));
    }
}
