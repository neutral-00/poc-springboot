package com.lousing.poc.config;

import com.lousing.poc.service.CounterNotificationService;
import com.lousing.poc.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ScopeConfig {

    // ✅ SINGLETON (default) - 1 shared instance
    @Bean
    @Scope("singleton")  // Default scope - 1 instance for entire app
    public NotificationService singletonNotificationService() {
        return new CounterNotificationService();
    }

    // ✅ PROTOTYPE - New instance EVERY time Spring creates bean
    @Bean
    @Scope("prototype")  // Fresh instance per request
    public NotificationService prototypeNotificationService() {
        return new CounterNotificationService();
    }
}
