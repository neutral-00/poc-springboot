package com.lousing.poc.service;

import com.lousing.poc.config.NotificationProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import  org.springframework.context.ApplicationContext;

@Service
public class ProfileDemoService {

    private final NotificationService emailService;
    private final NotificationProperties properties;

    public ProfileDemoService(
            ApplicationContext context,
            NotificationProperties properties
    ) {
        String profile = System.getProperty("spring.profiles.active", "dev");
        this.emailService = context.getBean(profile + "EmailService", NotificationService.class);
        this.properties = properties;
    }

    public void demoProfileSwitching() {
        System.out.println("\n🚀 === PROFILE SWITCHING DEMO ===");
        System.out.println("Active Profile: " + System.getProperty("spring.profiles.active", "dev"));
        System.out.println("Email Config: " + properties.getEmail().getApiKey());
        emailService.send("Profile-based service!", "user@company.com");
    }
}
