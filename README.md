# 1.2.4 Handle Dependencies between Beans

### Project Metadata
- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `1.2.3-handle-multiple-configuration-files`
- **Branch:** `1.2.4-handle-dependencies-between-beans`

### Learning Objectives
- [ ] Handle Dependencies between Beans (method parameters, `@DependsOn`)

**Scenario:** Create a **NotificationRouter** that depends on **all 3 notification services** (Email, SMS, Slack). Demonstrate **2 ways** Spring resolves bean dependencies.

## Step 1: Notification Router (Depends on All Services)

```java
// com.lousing.poc.service.NotificationRouter.java (NEW)
package com.lousing.poc.service;

import java.util.List;

public class NotificationRouter {
    private final List<NotificationService> notificationServices;
    
    public NotificationRouter(List<NotificationService> notificationServices) {
        this.notificationServices = notificationServices;
    }
    
    public void routeCriticalAlert(String message, String recipient) {
        System.out.println("\n🔄 Routing critical alert to all channels:");
        notificationServices.forEach(service -> service.send(message, recipient));
    }
    
    public void sendViaEmailOnly(String message, String recipient) {
        notificationServices.stream()
            .filter(service -> service instanceof EmailNotificationService)
            .forEach(service -> service.send(message, recipient));
    }
}
```

## Step 2: Updated Master Configuration (Method Parameter Injection)

```java
// com.lousing.poc.config.NotificationMasterConfig.java (UPDATED)
package com.lousing.poc.config;

import com.lousing.poc.service.NotificationRouter;
import com.lousing.poc.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.DependsOn;

@Configuration
@Import({EmailConfig.class, SmsConfig.class, SlackConfig.class})
public class NotificationMasterConfig {
    
    // ✅ WAY 1: Method parameter injection (Spring auto-wires ALL NotificationService beans)
    @Bean
    public NotificationRouter notificationRouter(List<NotificationService> allNotificationServices) {
        return new NotificationRouter(allNotificationServices);
    }
    
    // ✅ WAY 2: Explicit dependency injection (shows order control)
    @Bean
    @DependsOn({"emailNotificationService", "smsNotificationService", "slackNotificationService"})
    public NotificationRouter orderedNotificationRouter(
            NotificationService emailNotificationService,
            NotificationService smsNotificationService,
            NotificationService slackNotificationService) {
        return new NotificationRouter(List.of(
            emailNotificationService, smsNotificationService, slackNotificationService
        ));
    }
}
```

## Step 3: Dependency Demo Runner

```java
// com.lousing.poc.DependencyDemoRunner.java (NEW - Replaces MultiConfigDemoRunner)
package com.lousing.poc;

import com.lousing.poc.service.NotificationRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DependencyDemoRunner implements CommandLineRunner {
    
    private final ApplicationContext context;
    private final NotificationRouter router1;  // List injection
    private final NotificationRouter router2;  // Explicit injection
    
    public DependencyDemoRunner(
            ApplicationContext context,
            @Qualifier("notificationRouter") NotificationRouter router1,
            @Qualifier("orderedNotificationRouter") NotificationRouter router2
    ) {
        this.context = context;
        this.router1 = router1;
        this.router2 = router2;
    }
    
    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === BEAN DEPENDENCIES ===");
        
        demoListInjection();
        demoExplicitDependencies();
        demoBeanCreationOrder();
        
        System.out.println("✅ Dependency injection working!");
        System.out.println("----------------------------------");
    }
    
    private void demoListInjection() {
        System.out.println("\n1️⃣ List<NotificationService> injection:");
        router1.routeCriticalAlert("🚨 Server Down - List injection", "ops@company.com");
    }
    
    private void demoExplicitDependencies() {
        System.out.println("\n2️⃣ Explicit parameter injection:");
        router2.sendViaEmailOnly("Only email - explicit deps", "dev@company.com");
    }
    
    private void demoBeanCreationOrder() {
        System.out.println("\n3️⃣ @DependsOn order control:");
        System.out.println("NotificationRouter created AFTER all 3 services ✅");
    }
}
```

## Step 4: Bean Dependency Resolution Flow

```
Spring Container Boot Sequence:
1. EmailConfig.emailNotificationService() → Creates Email bean
2. SmsConfig.smsNotificationService()   → Creates SMS bean  
3. SlackConfig.slackNotificationService() → Creates Slack bean
4. NotificationMasterConfig.notificationRouter(List) 
   ├── Spring collects ALL NotificationService beans → [Email,SMS,Slack]
   └── Creates NotificationRouter ✅
5. @DependsOn ensures notificationRouter2 created LAST
```

## Expected Output

```bash
mvn spring-boot:run
```

```
✅ Multiple Config Files Loaded!
----------------------------------

🚀 === BEAN DEPENDENCIES ===
1️⃣ List<NotificationService> injection:
🔄 Routing critical alert to all channels:
📧 Email to ops@company.com: 🚨 Server Down - List injection
📱 SMS to ops@company.com: 🚨 Server Down - List injection
💬 Slack to #ops@company.com: 🚨 Server Down - List injection

2️⃣ Explicit parameter injection:
📧 Email to dev@company.com: Only email - explicit deps

3️⃣ @DependsOn order control:
NotificationRouter created AFTER all 3 services ✅

✅ Dependency injection working!
----------------------------------
```

## 2 Ways Spring Handles Bean Dependencies

| Method | Code | When Spring Injects |
|--------|------|--------------------|
| **List Injection** | `List<NotificationService> services` | **Auto-collects ALL** matching beans |
| **Explicit Params** | `NotificationService email, NotificationService sms` | **Exact bean name matching** |
| **@DependsOn** | `@DependsOn("bean1", "bean2")` | **Creation order control** |

## Key Learning Points

```
✅ List<Interface> = Spring collects ALL implementations automatically
✅ Method params = Spring matches by bean name or type
✅ @DependsOn = Explicit creation order (rarely needed)
✅ Spring resolves ALL dependencies BEFORE creating dependent bean
```

## File Structure (Branch 1.2.4)

```
1.2.4-handle-dependencies-between-beans/  (inherits from 1.2.3)
├── src/main/java/com/lousing/poc/
│   ├── DependencyDemoRunner.java         # NEW - Tests dependencies
│   ├── service/
│   │   └── NotificationRouter.java       # NEW - Depends on all services
│   └── config/
│       └── NotificationMasterConfig.java # UPDATED - Dependency injection
```

## Verification Checklist

**✅ Complete when:**
- [ ] `NotificationRouter` receives **all 3 services** via `List`
- [ ] **2 routers** created (List injection + explicit params)
- [ ] `mvn spring-boot:run` shows **Email + SMS + Slack** output
- [ ] `@DependsOn` demonstrates order control
- [ ] **No circular dependencies** (Spring would fail)

**Next: `1.2.5-explain-and-define-bean-scopes.md`** - Singleton vs Prototype scopes!

**🎯 Success:** Complex bean dependencies resolved via Java config! 🚀