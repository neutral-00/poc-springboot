# 1.3.1 Use External Properties to control Configuration

### Project Metadata

- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `main` (bare-bones Spring Boot app)
- **Branch:** `1.3.1-use-external-properties-to-control-configuration`

### Learning Objectives

- [ ] Use External Properties to control Configuration (`@Value`, `@ConfigurationProperties`)

**Scenario:** Build a **configurable notification gateway** that reads **API keys, endpoints, and feature flags** from external `application.properties` / `application.yml`. Switch between **Dev, Prod, and Test** environments without code changes.

## Step 1: Create External Properties Files

**application.properties (Default - Development):**

```properties
# com/lousing/poc/config/application.properties
app.name=Poc Spring Boot
notification.enabled=true
notification.email.api-key=dev-email-key-123
notification.email.endpoint=http://localhost:2525
notification.sms.enabled=false
notification.slack.enabled=true
notification.slack.webhook=https://hooks.slack.com/dev/abc123
logging.level.com.lousing.poc=DEBUG
```

**application-prod.properties (Production):**

```properties
app.name=Production Notification Gateway
notification.enabled=true
notification.email.api-key=prod-email-key-xyz789
notification.email.endpoint=https://api.email-prod.com
notification.sms.enabled=true
notification.sms.api-key=prod-sms-key-456
notification.slack.enabled=true
notification.slack.webhook=https://hooks.slack.com/prod/def456
logging.level.com.lousing.poc=INFO
```

## Step 2: Configuration Properties POJO

```java
// com.lousing.poc.config.NotificationProperties.java (NEW)
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
```

## Step 3: Properties Consumer Service

```java
// com.lousing.poc.service.PropertiesDemoService.java (NEW)
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
```

## Step 4: Demo Runner

```java
// com.lousing.poc.PropertiesDemoRunner.java (NEW)
package com.lousing.poc;

import com.lousing.poc.service.PropertiesDemoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PropertiesDemoRunner implements CommandLineRunner {

    private final PropertiesDemoService demoService;

    public PropertiesDemoRunner(PropertiesDemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public void run(String... args) {
        demoService.demoConfiguration();
        System.out.println("\n✅ External properties loaded successfully!");
        System.out.println("----------------------------------");
    }
}
```

## Step 5: Updated Main Application

```java
// com.lousing.poc.PocSpringbootApplication.java (UPDATED)
package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Properties & Profiles Ready!");
        System.out.println("----------------------------------");
    }
}
```

## Expected Output (Development)

```bash
mvn spring-boot:run
```

```
✅ Properties & Profiles Ready!
----------------------------------

🚀 === EXTERNAL PROPERTIES DEMO ===
📱 App: Poc Spring Boot
🔧 Config enabled: true

📧 Email Config:
   API Key: dev-email-key-123
   Endpoint: http://localhost:2525

📱 SMS Config: ❌

💬 Slack Config: ✅
   Webhook: https://hooks.slack.com/dev/abc123

✅ External properties loaded successfully!
----------------------------------
```

## Test Production Profile

```bash
mvn spring-boot:run -Dspring.profiles.active=prod
```
Or in Intellij, edit run configuration, under `Build and Run > Program arguments`, add:`--spring.profiles.active=prod`
```
📧 Email Config:
   API Key: prod-email-key-xyz789
   Endpoint: https://api.email-prod.com

📱 SMS Config: ✅
   API Key: prod-sms-key-456
```

## Property Sources Priority (Spring Boot Order)

```
1. Command line: --notification.email.api-key=override
2. SPRING_PROFILES_ACTIVE=prod → application-prod.properties
3. application.properties (default)
4. @ConfigurationProperties class
5. Code defaults
```

## Key Learning Points

```
✅ @Value("${property.name:default}") → Single properties
✅ @ConfigurationProperties(prefix="notification") → Complex nested config
✅ application-{profile}.properties → Environment-specific
✅ -Dspring.profiles.active=prod → Runtime profile selection
✅ Nested objects auto-mapped: notification.email.api-key → config.email.apiKey
```

## File Structure (Branch 1.3.1)

```
1.3.1-use-external-properties-to-control-configuration/
├── src/main/java/com/lousing/poc/
│   ├── PocSpringbootApplication.java      # Main app
│   ├── PropertiesDemoRunner.java          # NEW
│   ├── service/
│   │   └── PropertiesDemoService.java     # NEW
│   └── config/
│       └── NotificationProperties.java    # NEW
├── src/main/resources/
│   ├── application.properties             # NEW - Dev
│   └── application-prod.properties        # NEW - Prod
└── pom.xml
```

## Verification Checklist

**✅ Complete when:**

- [ ] **2 properties files** + **NotificationProperties** POJO created
- [ ] `@Value` and `@ConfigurationProperties` both working
- [ ] `mvn spring-boot:run -Dspring.profiles.active=prod` switches config
- [ ] **Nested properties** map correctly (`notification.slack.webhook`)
- [ ] **Feature flags** toggle services (`notification.sms.enabled=false`)

**Next: `1.3.2-demonstrate-purpose-of-profiles.md`** - Deep dive into Spring Profiles!

**🎯 Success:** Externalized configuration with zero code changes! 🚀
