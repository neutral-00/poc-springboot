# 1.2.2 Access Beans in the Application Context

### Project Metadata
- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `1.2.1-define-beans-using-java-code`
- **Branch:** `1.2.2-access-beans-in-application-context`

### Learning Objectives
- [ ] Access Beans in the Application Context (`getBean()` + `@Autowired`)

**Scenario:** Access our notification beans from **1.2.1** using **3 methods**: `ApplicationContext.getBean()`, `@Autowired` constructor injection, and `@Autowired` field injection.

## Step 1: Bean Consumer (CommandLineRunner)

```java
// com.lousing.poc.BeanDemoRunner.java (NEW FILE)
package com.lousing.poc;

import com.lousing.poc.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component  // Spring will create & wire this bean automatically
public class BeanDemoRunner implements CommandLineRunner {
    
    private final ApplicationContext context;
    private final NotificationService emailService;
    private final NotificationService smsService;
    
    // 1️⃣ Constructor Injection (PREFERRED)
    public BeanDemoRunner(
        ApplicationContext context,
        @Qualifier("emailNotificationService") NotificationService emailService,
        @Qualifier("smsNotificationService") NotificationService smsService
    ) {
        this.context = context;
        this.emailService = emailService;
        this.smsService = smsService;
    }
    
    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === ACCESSING BEANS ===");
        
        // METHOD 1: ApplicationContext.getBean() - Programmatic
        demoGetBean();
        
        // METHOD 2: Constructor Injection (already wired above)
        demoConstructorInjection();
        
        // METHOD 3: Field Injection (alternative - less preferred)
        demoFieldInjection();
        
        System.out.println("✅ All bean access methods working!");
        System.out.println("----------------------------------");
    }
    
    private void demoGetBean() {
        System.out.println("\n1️⃣ ApplicationContext.getBean():");
        
        // By TYPE (ambiguous - multiple NotificationService beans)
        // NotificationService any = context.getBean(NotificationService.class);
        
        // By NAME (method name from 1.2.1)
        NotificationService emailByName = context.getBean("emailNotificationService", NotificationService.class);
        emailByName.send("Manual lookup test", "dev@company.com");
        
        // By CLASS (exact implementation)
        EmailNotificationService emailImpl = context.getBean(EmailNotificationService.class);
        emailImpl.send("By class lookup", "admin@company.com");
    }
    
    private void demoConstructorInjection() {
        System.out.println("\n2️⃣ Constructor Injection:");
        emailService.send("Constructor injected", "user1@example.com");
        smsService.send("Constructor injected", "+1234567890");
    }
    
    private void demoFieldInjection() {
        System.out.println("\n3️⃣ Field Injection:");
        testField.send("Field injected", "test@example.com");
    }
    
    // 3️⃣ Field Injection example (@Autowired on field)
    @Autowired
    @Qualifier("smsNotificationService")
    private NotificationService testField;
}
```

## Step 2: Updated Main Application

```java
// com.lousing.poc.PocSpringbootApplication.java (UPDATED)
package com.lousing.poc;

import com.lousing.poc.config.NotificationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(NotificationConfig.class)
public class PocSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Beans configured via Java Config!");
        System.out.println("----------------------------------");
    }
}
```

## Expected Output

```bash
mvn spring-boot:run
```

```
✅ Beans configured via Java Config!
----------------------------------

🚀 === ACCESSING BEANS ===
1️⃣ ApplicationContext.getBean():
📧 Email to dev@company.com: Manual lookup test
📧 Email to admin@company.com: By class lookup

2️⃣ Constructor Injection:
📧 Email to user1@example.com: Constructor injected
📱 SMS to +1234567890: Constructor injected

3️⃣ Field Injection:
📱 SMS to test@example.com: Field injected

✅ All bean access methods working!
----------------------------------
```

## 3 Ways to Access Beans (Comparison)

| Method | Code | Pros | Cons |
|--------|------|------|------|
| **getBean()** | `context.getBean("name", Type.class)` | Programmatic, runtime lookup | Verbose, error-prone |
| **Constructor** | `public Class(Dependency dep)` | **Immutable, testable, preferred** | More constructor params |
| **Field @Autowired** | `@Autowired Dependency dep;` | Simple | Mutable, harder to test |

## Key Learning Points

```
✅ ApplicationContext.getBean():
   ├── getBean("beanName", Type.class)  ← By name (method name)
   ├── getBean(Type.class)              ← By type (ambiguous → fails)
   └── getBean(ImplClass.class)         ← Exact implementation

✅ Constructor injection = @Qualifier for multiple beans
✅ Field injection = @Autowired + @Qualifier
✅ Spring auto-creates @Component BeanDemoRunner & wires it!
```

## File Structure (Branch 1.2.2)

```
1.2.2-access-beans-in-application-context/  (inherits from 1.2.1)
├── src/main/java/com/lousing/poc/
│   ├── PocSpringbootApplication.java      # Updated main
│   ├── BeanDemoRunner.java               # NEW - Bean access
│   ├── service/                          # From 1.2.1
│   └── config/                           # From 1.2.1
└── pom.xml
```

## Verification Checklist

**✅ Complete when:**
- [ ] `BeanDemoRunner` demonstrates **all 3 access methods**
- [ ] `mvn spring-boot:run` shows **email + SMS output**
- [ ] Constructor injection uses `@Qualifier`
- [ ] Field injection uses `@Autowired`
- [ ] `getBean("emailNotificationService")` works
- [ ] **No changes** to `NotificationConfig` from 1.2.1

**Next: `1.2.3-handle-multiple-configuration-files.md`** - `@Import` multiple `@Configuration` classes!

**🎯 Success:** All 3 bean access patterns working on top of pure Java config! 🚀