package com.lousing.poc;

import com.lousing.poc.config.NotificationProperties;
import com.lousing.poc.service.ProfileDemoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProfileDemoRunner implements CommandLineRunner {

    private final NotificationProperties properties;
    private final ProfileDemoService profileDemo;

    public ProfileDemoRunner(NotificationProperties properties, ProfileDemoService profileDemo) {
        this.properties = properties;
        this.profileDemo = profileDemo;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n📱 App: " + properties.getEmail().getApiKey());
        profileDemo.demoProfileSwitching();
        System.out.println("\n✅ Profiles working!");
        System.out.println("----------------------------------");
    }
}
