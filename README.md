# 1.2.3 Handle multiple Configuration files

### Project Metadata
- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `1.2.2-access-beans-in-application-context`
- **Branch:** `1.2.3-handle-multiple-configuration-files`

### Learning Objectives
- [ ] Handle multiple Configuration files (`@Import`, `@ComponentScan`, `@Configuration` hierarchy)

**Scenario:** Split notification config into **separate modules** (EmailConfig, SmsConfig, SlackConfig). Learn **3 ways** to combine multiple `@Configuration` classes.

## Step 1: Create Modular Configuration Files

```java
// com.lousing.poc.config.EmailConfig.java (NEW)
package com.lousing.poc.config;

import com.lousing.poc.service.EmailNotificationService;
import com.lousing.poc.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Module 1: Email only
public class EmailConfig {
    @Bean
    public NotificationService emailNotificationService() {
        return new EmailNotificationService();
    }
}
```

```java
// com.lousing.poc.config.SmsConfig.java (NEW)
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
```

```java
// com.lousing.poc.config.SlackConfig.java (NEW)
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
```

```java
// com.lousing.poc.service.SlackNotificationService.java (NEW)
package com.lousing.poc.service;

public class SlackNotificationService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("💬 Slack to #" + recipient + ": " + message);
    }
}
```

## Step 2: Master Configuration (Combines All Modules)

```java
// com.lousing.poc.config.NotificationMasterConfig.java (NEW)
package com.lousing.poc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({EmailConfig.class, SmsConfig.class, SlackConfig.class})  // ✅ Method 1: @Import
public class NotificationMasterConfig {
    // All 3 configs imported here
}
```

## Step 3: Updated Demo Runner

```java
// com.lousing.poc.MultiConfigDemoRunner.java (NEW - Replaces BeanDemoRunner)
package com.lousing.poc;

import com.lousing.poc.service.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MultiConfigDemoRunner implements CommandLineRunner {
    
    private final ApplicationContext context;
    
    public MultiConfigDemoRunner(ApplicationContext context) {
        this.context = context;
    }
    
    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === MULTIPLE CONFIG FILES ===");
        
        // Demo all 3 beans from 3 different config files
        demoAllNotificationServices();
        demoBeanCount();
        
        System.out.println("✅ Multiple configs working!");
        System.out.println("----------------------------------");
    }
    
    private void demoAllNotificationServices() {
        System.out.println("\n📋 Beans from 3 config files:");
        
        NotificationService email = context.getBean("emailNotificationService", NotificationService.class);
        email.send("Email config test", "dev@company.com");
        
        NotificationService sms = context.getBean("smsNotificationService", NotificationService.class);
        sms.send("SMS config test", "+1234567890");
        
        NotificationService slack = context.getBean("slackNotificationService", NotificationService.class);
        slack.send("Slack config test", "dev-channel");
    }
    
    private void demoBeanCount() {
        String[] beanNames = context.getBeanDefinitionNames();
        long notificationBeans = java.util.Arrays.stream(beanNames)
            .filter(name -> name.contains("NotificationService"))
            .count();
        
        System.out.println("\n📊 Total NotificationService beans: " + notificationBeans);
    }
}
```

## Step 4: Updated Main Application

```java
// com.lousing.poc.PocSpringbootApplication.java (UPDATED)
package com.lousing.poc;

import com.lousing.poc.config.NotificationMasterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(NotificationMasterConfig.class)  // ✅ Single import loads ALL configs
public class PocSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Multiple Config Files Loaded!");
        System.out.println("----------------------------------");
    }
}
```

## Expected Output

```bash
mvn spring-boot:run
```

```
✅ Multiple Config Files Loaded!
----------------------------------

🚀 === MULTIPLE CONFIG FILES ===
📋 Beans from 3 config files:
📧 Email to dev@company.com: Email config test
📱 SMS to +1234567890: SMS config test
💬 Slack to #dev-channel: Slack config test

📊 Total NotificationService beans: 3

✅ Multiple configs working!
----------------------------------
```

## 3 Ways to Handle Multiple Configs

| Method | Code | Use Case |
|--------|------|----------|
| **@Import** | `@Import({Config1.class, Config2.class})` | **Recommended** - Explicit |
| **@ComponentScan** | `@ComponentScan("com.lousing.poc.config")` | Auto-discover `@Configuration` |
| **XML** | `<import resource="sms-config.xml"/>` | Legacy |

## Key Learning Points

```
✅ Modular configs = One concern per file
✅ @Import cascades: NotificationMasterConfig → EmailConfig + SmsConfig + SlackConfig
✅ Bean names preserved: "emailNotificationService", "smsNotificationService", etc.
✅ Spring merges ALL configs into single ApplicationContext
```

## File Structure (Branch 1.2.3)

```
1.2.3-handle-multiple-configuration-files/  (inherits from 1.2.2)
├── src/main/java/com/lousing/poc/
│   ├── PocSpringbootApplication.java          # @Import MasterConfig
│   ├── MultiConfigDemoRunner.java            # NEW - Tests all configs
│   ├── service/
│   │   └── SlackNotificationService.java     # NEW
│   └── config/
│       ├── NotificationMasterConfig.java     # NEW - @Import all
│       ├── EmailConfig.java                  # NEW
│       ├── SmsConfig.java                    # NEW  
│       └── SlackConfig.java                  # NEW
```

## Verification Checklist

**✅ Complete when:**
- [ ] **4 new config files** + **Slack service** created
- [ ] `mvn spring-boot:run` shows **Email + SMS + Slack** output
- [ ] `@Import(NotificationMasterConfig.class)` loads **all 3 configs**
- [ ] `getBean("slackNotificationService")` works
- [ ] **3 NotificationService beans** detected

**Next: `1.2.4-handle-dependencies-between-beans.md`** - Bean method parameters + `@DependsOn`!

**🎯 Success:** Clean modular Java config architecture! 🚀