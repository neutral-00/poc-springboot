package com.lousing.poc.config;

import com.lousing.poc.service.NotificationService;
import com.lousing.poc.service.SlackNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Module 3: Slack only
public class SlackConfig {
    @Bean
    public NotificationService slackNotificationService() {
        return new SlackNotificationService();
    }
}
