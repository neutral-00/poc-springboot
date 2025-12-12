# 1.2.1 Define Spring Beans using Java code

### Project Metadata

- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- Branch: `1.2.1-define-beans-using-java-code`

### Learning Objectives

- [ ] Define Spring Beans using Java code (`@Configuration` + `@Bean`)

**Scenario:** Build notification services (Email + SMS) configured **purely** via Java config. No `@Component` annotations - only `@Configuration` + `@Bean`.

## Step 1: Domain & Service Interfaces

```java
// com.lousing.poc.service.NotificationService.java
package com.lousing.poc.service;

public interface NotificationService {
    void send(String message, String recipient);
}
```

## Step 2: Service Implementations (Plain POJOs)

```java
// com.lousing.poc.service.EmailNotificationService.java
package com.lousing.poc.service;

public class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("📧 Email to " + recipient + ": " + message);
    }
}
```

```java
// com.lousing.poc.service.SmsNotificationService.java
package com.lousing.poc.service;

public class SmsNotificationService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("📱 SMS to " + recipient + ": " + message);
    }
}
```

## Step 3: Java Configuration (Bean Factory)

```java
// com.lousing.poc.config.NotificationConfig.java
package com.lousing.poc.config;

import com.lousing.poc.service.EmailNotificationService;
import com.lousing.poc.service.NotificationService;
import com.lousing.poc.service.SmsNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Spring: "This is a bean factory"
public class NotificationConfig {

    // Bean 1: Email service (bean name = "emailNotificationService")
    @Bean
    public NotificationService emailNotificationService() {
        return new EmailNotificationService();
    }

    // Bean 2: SMS service (bean name = "smsNotificationService")
    @Bean
    public NotificationService smsNotificationService() {
        return new SmsNotificationService();
    }
}
```

## Step 4: Spring Boot Application

```java
// com.lousing.poc.PocSpringbootApplication.java
package com.lousing.poc;

import com.lousing.poc.config.NotificationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(NotificationConfig.class)  // Load our Java config
public class PocSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Beans configured via Java Config!");
    }
}
```

## Bean Lifecycle (What Spring Does)

```
1. Spring Boot starts → Scans @Import(NotificationConfig.class)
2. Calls emailNotificationService() → Creates EmailNotificationService bean
3. Calls smsNotificationService() → Creates SmsNotificationService bean
4. Stores in ApplicationContext:
   ├── emailNotificationService → EmailNotificationService instance
   └── smsNotificationService → SmsNotificationService instance ✅
```

## Verification (Run & Check)

```bash
mvn spring-boot:run
```

**Expected Output:**

```
✅ Beans configured via Java Config!
Started PocSpringbootApplication in X.XXX seconds
```

**Spring Container State:**

```
ApplicationContext Beans:
├── emailNotificationService (EmailNotificationService)
├── smsNotificationService (SmsNotificationService)
└── 20+ Spring Boot auto-config beans
```

## Key Learning Points

```
✅ @Configuration = Bean Factory Class
✅ @Bean method:
   ├── Method name = Bean name ("emailNotificationService")
   ├── Return value = Bean instance
   └── Spring calls method → Manages returned object

✅ @Import loads config into Spring context
✅ No @Component/@Service needed - Pure Java config!
```

## File Structure

```
1.2.1-define-beans-using-java-code/
├── src/main/java/com/lousing/poc/
│   ├── PocSpringbootApplication.java      # @Import config
│   ├── service/
│   │   ├── NotificationService.java
│   │   ├── EmailNotificationService.java
│   │   └── SmsNotificationService.java
│   └── config/
│       └── NotificationConfig.java        # @Configuration + @Bean
└── pom.xml
```

## Verification Checklist

**✅ Complete when:**

- [ ] 5 files created with exact package structure
- [ ] `mvn spring-boot:run` starts successfully
- [ ] `@Import(NotificationConfig.class)` loads beans
- [ ] No `@Component` annotations used
- [ ] Beans defined **purely** via `@Bean` methods
- [ ] Ready as parent for `1.2.2-access-beans-in-application-context`

**Next: `1.2.2-access-beans-in-the-application-context.md`** - Access these beans via `ApplicationContext.getBean()` + `@Autowired`!

**🎯 Foundation ready:** Pure Java config beans defined! Next: Accessing them.
