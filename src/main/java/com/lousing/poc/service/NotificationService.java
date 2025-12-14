package com.lousing.poc.service;

import org.springframework.stereotype.Service;

public interface NotificationService {
    void send(String message, String recipient);
}
