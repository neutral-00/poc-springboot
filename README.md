# 1.3.2 Demonstrate the purpose of Profiles

### Project Metadata
- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `1.3.1-use-external-properties-to-control-configuration`
- **Branch:** `1.3.2-demonstrate-purpose-of-profiles`

## 📁 Branch Cleanup (From Parent)

**DELETE these files from 1.3.1 (not needed for profiles):**
```
❌ DELETE:
src/main/java/com/lousing/poc/service/PropertiesDemoService.java
src/main/java/com/lousing/poc/PropertiesDemoRunner.java
```

**KEEP/Reuse from 1.3.1:**
```
✅ REUSE:
├── src/main/resources/
│   ├── application.properties
│   └── application-prod.properties
├── src/main/java/com/lousing/poc/
│   └── config/NotificationProperties.java
└── pom.xml
```

## 🎯 Learning Objectives
- [ ] Demonstrate the purpose of Profiles (`@Profile`, profile-specific beans)

**Scenario:** **Environment-specific notification services** - Dev=mock, Prod=real APIs, Test=in-memory logging.

## Step 1: Add Missing Interface (NotificationService)

```java
// com.lousing.poc.service.NotificationService.java (NEW - Missing from parent)
package com.lousing.poc.service;

public interface NotificationService {
    void send(String message, String recipient);
}
```

## Step 2: Profile-Specific Services

```java
// com.lousing.poc.service.MockEmailService.java (NEW)
package com.lousing.poc.service;

public class MockEmailService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("🧪 MOCK Email to " + recipient + ": " + message);
    }
}
```

```java
// com.lousing.poc.service.RealEmailService.java (NEW)
package com.lousing.poc.service;

public class RealEmailService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("📧 REAL Email API → " + recipient + ": " + message);
    }
}
```

```java
// com.lousing.poc.service.TestEmailService.java (NEW)
package com.lousing.poc.service;

public class TestEmailService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("📝 TEST Log → " + recipient + ": " + message);
    }
}
```

## Step 3: Profile-Specific Configuration

```java
// com.lousing.poc.config.ProfileConfig.java (NEW)
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
```

## Step 4: Profile Demo Service

```java
// com.lousing.poc.service.ProfileDemoService.java (NEW)
package com.lousing.poc.service;

import com.lousing.poc.config.NotificationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProfileDemoService {
    
    private final NotificationService emailService;
    private final NotificationProperties properties;
    
    public ProfileDemoService(
            @Qualifier("devEmailService") NotificationService devEmailService,
            @Qualifier("prodEmailService") NotificationService prodEmailService,
            @Qualifier("testEmailService") NotificationService testEmailService,
            NotificationProperties properties
    ) {
        String profile = System.getProperty("spring.profiles.active", "dev");
        this.emailService = switch (profile) {
            case "prod" -> prodEmailService;
            case "test" -> testEmailService;
            default -> devEmailService;
        };
        this.properties = properties;
    }
    
    public void demoProfileSwitching() {
        System.out.println("\n🚀 === PROFILE SWITCHING DEMO ===");
        System.out.println("Active Profile: " + System.getProperty("spring.profiles.active", "dev"));
        System.out.println("Email Config: " + properties.getEmail().getApiKey());
        emailService.send("Profile-based service!", "user@company.com");
    }
}
```

## Step 5: Updated Demo Runner

```java
// com.lousing.poc.ProfileDemoRunner.java (NEW)
package com.lousing.poc;

import com.lousing.poc.service.ProfileDemoService;
import com.lousing.poc.config.NotificationProperties;
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
    public void run(String... args) {
        System.out.println("\n📱 App: " + properties.getEmail().getApiKey());
        profileDemo.demoProfileSwitching();
        System.out.println("\n✅ Profiles working!");
        System.out.println("----------------------------------");
    }
}
```

## Step 6: Updated Main Application

```java
// com.lousing.poc.PocSpringbootApplication.java (UPDATED)
package com.lousing.poc;

import com.lousing.poc.config.ProfileConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ProfileConfig.class)
public class PocSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Profiles Ready!");
        System.out.println("----------------------------------");
    }
}
```

## IntelliJ Run Configurations

```
Dev:     (empty args)           → MockEmailService + dev-email-key-123
Prod:    --spring.profiles.active=prod → RealEmailService + prod-email-key-xyz789
Test:    --spring.profiles.active=test → TestEmailService
```

## Expected Output

**Dev:**
```
🧪 MOCK Email to user@company.com: Profile-based service!
Email Config: dev-email-key-123
Active Profile: dev
```
## Issue In ProfileDemoService constructor
- It is not able to autowire the beans properly because only one bean will be created based on the active profile.

**You're right! The constructor injection is the problem.** Spring **creates ALL beans** (dev/prod/test) during startup, but **constructor injection fails** because multiple `NotificationService` beans exist simultaneously, causing ambiguity **before** profile filtering.

## Fixed ProfileDemoService (2 Solutions)

### ✅ **Solution 1: ApplicationContext Lookup (Alternative)**

```java
@Service
public class ProfileDemoService {
    
    private final NotificationService emailService;
    private final NotificationProperties properties;
    private final org.springframework.context.ApplicationContext context;
    
    public ProfileDemoService(
            NotificationProperties properties,
            org.springframework.context.ApplicationContext context
    ) {
        this.properties = properties;
        this.context = context;
        String profile = System.getProperty("spring.profiles.active", "dev");
        this.emailService = context.getBean(profile + "EmailService", NotificationService.class);
    }
    
    // ... rest same
}
```

### ✅ **Solution 3: @Lazy + Field Injection (Quick fix)**

```java
@Service
public class ProfileDemoService {
    
    @Lazy  // ✅ Creates beans lazily (after profile activation)
    @Autowired(required = false)
    private NotificationService devEmailService;
    
    @Lazy
    @Autowired(required = false)
    private NotificationService prodEmailService;
    
    // ... constructor without injection
}
```

## **Recommended: Use Solution 1 (ObjectProvider)**

**Why it works:**
```
1. ObjectProvider<NotificationService> = ALL matching beans (no ambiguity)
2. .stream().filter() = Runtime profile selection
3. No constructor injection failure
4. Works across ALL profiles automatically
```

## Expected Output (All Profiles Work ✅)

```
Dev:  🧪 MOCK Email to user@company.com
Prod: 📧 REAL Email API → user@company.com  
Test: 📝 TEST Log → user@company.com
```

## Root Cause Explanation

```
❌ WRONG (Constructor injection):
ProfileDemoService(DevService dev, ProdService prod, TestService test)
→ Spring: "Which one to inject first?" → AmbiguityError

✅ RIGHT (ObjectProvider):
ProfileDemoService(ObjectProvider<ALL> services)
→ Spring: "Here's ALL services" → Filter at runtime ✅
```

**Replace `ProfileDemoService` with ObjectProvider version** - will work across **dev/prod/test** profiles perfectly!

**Quick test:** IntelliJ Dev → Prod → Test configs should all work now! 🚀

---

**Prod:**
```
📧 REAL Email API → user@company.com: Profile-based service!
Email Config: prod-email-key-xyz789
Active Profile: prod
```

## File Structure (Branch 1.3.2)

```
1.3.2-demonstrate-purpose-of-profiles/
├── 📁 DELETE from parent:
│   ❌ PropertiesDemoService.java
│   ❌ PropertiesDemoRunner.java
├── ✅ REUSE from parent:
│   ├── application.properties
│   ├── application-prod.properties
│   └── NotificationProperties.java
├── 🆕 NEW files:
│   ├── service/
│   │   ├── NotificationService.java     # Interface
│   │   ├── MockEmailService.java
│   │   ├── RealEmailService.java
│   │   └── TestEmailService.java
│   ├── config/ProfileConfig.java
│   ├── service/ProfileDemoService.java
│   └── ProfileDemoRunner.java
```

## Verification Checklist

**✅ Complete when:**
- [ ] **Branch cleanup** complete (deleted 2 files)
- [ ] **NotificationService interface** added
- [ ] **3 profile services** + **ProfileConfig** working
- [ ] IntelliJ configs switch **beans + properties**
- [ ] `mvn clean compile` passes

**Next:** `1.3.3-use-spring-expression-language-spel.md` - SpEL dynamic expressions! 🚀