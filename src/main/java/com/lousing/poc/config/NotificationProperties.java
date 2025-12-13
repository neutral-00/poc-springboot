package com.lousing.poc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "notification")  // Maps notification.* properties
public class NotificationProperties {
    private boolean enabled = true;
    private Email email = new Email();
    private Sms sms = new Sms();
    private Slack slack = new Slack();

    // Getters/Setters
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Email getEmail() { return email; }
    public void setEmail(Email email) { this.email = email; }

    public Sms getSms() { return sms; }
    public void setSms(Sms sms) { this.sms = sms; }

    public Slack getSlack() { return slack; }
    public void setSlack(Slack slack) { this.slack = slack; }

    // Nested configuration classes
    public static class Email {
        private String apiKey = "";
        private String endpoint = "";

        public String getApiKey() { return apiKey; }
        public void setApiKey(String apiKey) { this.apiKey = apiKey; }

        public String getEndpoint() { return endpoint; }
        public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    }

    public static class Sms {
        private boolean enabled = false;
        private String apiKey = "";

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }

        public String getApiKey() { return apiKey; }
        public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    }

    public static class Slack {
        private boolean enabled = false;
        private String webhook = "";

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }

        public String getWebhook() { return webhook; }
        public void setWebhook(String webhook) { this.webhook = webhook; }
    }
}
