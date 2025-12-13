package com.lousing.poc.service;

import com.lousing.poc.config.NotificationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesDemoService {
    // ✅ WAY 1: @Value for individual properties
    @Value("${app.name:Default App}")
    private String appName;

    // ✅ WAY 2: @ConfigurationProperties POJO (preferred for complex config)
    private final NotificationProperties config;

    public PropertiesDemoService(NotificationProperties config) {
        this.config = config;
    }

    public void demoConfiguration() {
        System.out.println("\n🚀 === EXTERNAL PROPERTIES DEMO ===");
        System.out.println("📱 App: " + appName);
        System.out.println("🔧 Config enabled: " + config.isEnabled());

        System.out.println("\n📧 Email Config:");
        System.out.println("   API Key: " + config.getEmail().getApiKey());
        System.out.println("   Endpoint: " + config.getEmail().getEndpoint());

        System.out.println("\n📱 SMS Config: " + (config.getSms().isEnabled() ? "✅" : "❌"));
        if (config.getSms().isEnabled()) {
            System.out.println("   API Key: " + config.getSms().getApiKey());
        }

        System.out.println("\n💬 Slack Config: " + (config.getSlack().isEnabled() ? "✅" : "❌"));
        System.out.println("   Webhook: " + config.getSlack().getWebhook());
    }
}
