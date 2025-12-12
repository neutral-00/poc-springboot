package com.lousing.poc.config;

import com.lousing.poc.service.EmailNotificationService;
import com.lousing.poc.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Module 1: Email only
public class EmailConfig {
    @Bean
    public NotificationService emailNotificationService() {
        return new EmailNotificationService();
    }
}
