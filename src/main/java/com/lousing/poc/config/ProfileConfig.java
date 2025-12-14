package com.lousing.poc.config;

import com.lousing.poc.service.MockEmailService;
import com.lousing.poc.service.NotificationService;
import com.lousing.poc.service.RealEmailService;
import com.lousing.poc.service.TestEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    // DEV: Mock services
    @Bean
    @Profile("dev")
    public NotificationService devEmailService() {
        return new MockEmailService();
    }

    // PROD: Real services
    @Bean
    @Profile("prod")
    public NotificationService prodEmailService() {
        return new RealEmailService();
    }

    // TEST: Test services
    @Bean
    @Profile("test")
    public NotificationService testEmailService() {
        return new TestEmailService();
    }
}
