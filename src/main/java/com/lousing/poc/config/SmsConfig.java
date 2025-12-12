package com.lousing.poc.config;

import com.lousing.poc.service.NotificationService;
import com.lousing.poc.service.SmsNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Module 2: SMS only
public class SmsConfig {

    @Bean
    public NotificationService smsNotificationService() {
        return new SmsNotificationService();
    }
}
