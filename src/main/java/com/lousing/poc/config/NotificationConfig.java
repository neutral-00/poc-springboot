package com.lousing.poc.config;

import com.lousing.poc.service.EmailNotificationService;
import com.lousing.poc.service.NotificationService;
import com.lousing.poc.service.SmsNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Spring: "This is a bean factory"
public class NotificationConfig {

    // Bean 1: Email service (bean name = "emailNotificationService")
    @Bean
    public NotificationService emailNotificationService() {
        return new EmailNotificationService();
    }

    // Bean 2: SMS service (bean name = "smsNotificationService")
    @Bean
    public NotificationService smsNotificationService() {
        return new SmsNotificationService();
    }
}