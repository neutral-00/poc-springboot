# 1.2.5 Explain and define Bean Scopes

### Project Metadata
- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `1.2.1-define-beans-using-java-code` (minimal + clean)
- **Branch:** `1.2.5-explain-and-define-bean-scopes`

### Learning Objectives
- [ ] Explain and define Bean Scopes (`singleton`, `prototype`)

**Scenario:** Demonstrate **singleton** (default - shared instance) vs **prototype** (new instance each time) scopes using our notification services.

## Pre-requisites
- Java Config knowledge from **1.2.1**

## Clean up Before Starting
- Delete the file `com.lousing.poc.service.EmailNotificationService.java` (not needed here)
- Delete the file `com.loussing.poc.service.SmsNotificationService.java` (not needed here)
- Delete the file `com.lousing.poc.config.NotificationConfig.java` (not needed here)

## Step 1: Scope Demo Services

```java
// com.lousing.poc.service.CounterNotificationService.java (NEW)
package com.lousing.poc.service;

public class CounterNotificationService implements NotificationService {
    private static int instanceCount = 0;
    private final int instanceId;
    
    public CounterNotificationService() {
        this.instanceId = ++instanceCount;
        System.out.println("🔢 Created CounterNotificationService #" + instanceId);
    }
    
    @Override
    public void send(String message, String recipient) {
        System.out.println("🔢 #" + instanceId + " → " + recipient + ": " + message);
    }
    
    public int getInstanceId() {
        return instanceId;
    }
}
```

## Step 2: Scope Configuration

```java
// com.lousing.poc.config.ScopeConfig.java (NEW)
package com.lousing.poc.config;

import com.lousing.poc.service.CounterNotificationService;
import com.lousing.poc.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ScopeConfig {
    
    // ✅ SINGLETON (default) - 1 shared instance
    @Bean
    @Scope("singleton")  // Default scope - 1 instance for entire app
    public NotificationService singletonNotificationService() {
        return new CounterNotificationService();
    }
    
    // ✅ PROTOTYPE - New instance EVERY time Spring creates bean
    @Bean
    @Scope("prototype")  // Fresh instance per request
    public NotificationService prototypeNotificationService() {
        return new CounterNotificationService();
    }
}
```

## Step 3: Scope Demo Runner

```java
// com.lousing.poc.ScopeDemoRunner.java (NEW)
package com.lousing.poc;

import com.lousing.poc.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ScopeDemoRunner implements CommandLineRunner {
    
    private final ApplicationContext context;
    private final NotificationService singletonBean;
    
    // ObjectFactory for prototype - gets NEW instance each call
    @Autowired
    private ObjectFactory<NotificationService> prototypeFactory;
    
    public ScopeDemoRunner(
            ApplicationContext context,
            @Qualifier("singletonNotificationService") NotificationService singletonBean
    ) {
        this.context = context;
        this.singletonBean = singletonBean;
    }
    
    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === BEAN SCOPES DEMO ===");
        
        demoSingletonScope();
        demoPrototypeScope();
        demoObjectFactory();
        
        System.out.println("✅ Bean scopes demonstrated!");
        System.out.println("----------------------------------");
    }
    
    private void demoSingletonScope() {
        System.out.println("\n1️⃣ SINGLETON Scope (default):");
        System.out.println("   • 1 instance shared everywhere");
        
        NotificationService singleton1 = context.getBean("singletonNotificationService", NotificationService.class);
        NotificationService singleton2 = context.getBean("singletonNotificationService", NotificationService.class);
        
        System.out.println("singleton1.id = " + ((CounterNotificationService) singleton1).getInstanceId());
        System.out.println("singleton2.id = " + ((CounterNotificationService) singleton2).getInstanceId());
        System.out.println("✅ SAME instance (singleton)!");
        
        singleton1.send("Singleton test", "shared@company.com");
    }
    
    private void demoPrototypeScope() {
        System.out.println("\n2️⃣ PROTOTYPE Scope:");
        System.out.println("   • NEW instance every time");
        
        NotificationService proto1 = context.getBean("prototypeNotificationService", NotificationService.class);
        NotificationService proto2 = context.getBean("prototypeNotificationService", NotificationService.class);
        
        System.out.println("proto1.id = " + ((CounterNotificationService) proto1).getInstanceId());
        System.out.println("proto2.id = " + ((CounterNotificationService) proto2).getInstanceId());
        System.out.println("✅ DIFFERENT instances (prototype)!");
    }
    
    private void demoObjectFactory() {
        System.out.println("\n3️⃣ ObjectFactory<Prototype> (for injection):");
        
        NotificationService factory1 = prototypeFactory.getObject();
        NotificationService factory2 = prototypeFactory.getObject();
        
        System.out.println("factory1.id = " + ((CounterNotificationService) factory1).getInstanceId());
        System.out.println("factory2.id = " + ((CounterNotificationService) factory2).getInstanceId());
        System.out.println("✅ NEW instances each factory.getObject()!");
    }
}
```

## Step 4: Updated Main Application

```java
// com.lousing.poc.PocSpringbootApplication.java (UPDATED)
package com.lousing.poc;

import com.lousing.poc.config.ScopeConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ScopeConfig.class) // Load our Java config
public class PocSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Beans configured via Java Config!");
        System.out.println("----------------------------------");
    }
}
```

## Expected Output

```
✅ Beans configured via Java Config!
----------------------------------

🚀 === BEAN SCOPES DEMO ===
🔢 Created CounterNotificationService #1
🔢 Created CounterNotificationService #2
🔢 Created CounterNotificationService #3

1️⃣ SINGLETON Scope (default):
singleton1.id = 1
singleton2.id = 1
✅ SAME instance (singleton)!
🔢 #1 → shared@company.com: Singleton test

2️⃣ PROTOTYPE Scope:
proto1.id = 4
proto2.id = 5
✅ DIFFERENT instances (prototype)!

3️⃣ ObjectFactory<Prototype> (for injection):
factory1.id = 6
factory2.id = 7
✅ NEW instances each factory.getObject()!

✅ Bean scopes demonstrated!
----------------------------------
```

## Bean Scopes Summary

| Scope | `@Scope` | Behavior | Use Case |
|-------|----------|----------|----------|
| **singleton** | `default` | **1 instance per container** | Services, Repositories |
| **prototype** | `"prototype"` | **New instance per request** | Stateful objects, Caches |

## Key Learning Points

```
✅ singleton: context.getBean() → SAME instance every time
✅ prototype: context.getBean() → NEW instance every time  
✅ ObjectFactory<Prototype>: DI-friendly prototype injection
✅ Scope applies to @Bean return value
✅ Creation happens on FIRST access (lazy by default)
```

## File Structure (Branch 1.2.5)

```
1.2.5-explain-and-define-bean-scopes/  (inherits from 1.2.1)
├── src/main/java/com/lousing/poc/
│   ├── PocSpringbootApplication.java      # @Import ScopeConfig
│   ├── ScopeDemoRunner.java              # NEW - Tests scopes
│   ├── service/
│   │   └── CounterNotificationService.java # NEW - Tracks instances
│   └── config/
│       └── ScopeConfig.java              # NEW - @Scope definitions
```

## Verification Checklist

**✅ Complete when:**
- [ ] **Singleton** returns **same instance ID** on multiple `getBean()`
- [ ] **Prototype** returns **different instance IDs**
- [ ] `ObjectFactory.getObject()` creates **new prototypes**
- [ ] **5+ unique instances** created during demo
- [ ] `mvn spring-boot:run` shows **creation timestamps**

## One Issue to Note
**One Error in ObjectFactory Section! The error occurs because field injection of `prototypeFatory` is ambiguous.** Spring finds **2 `NotificationService` beans** (singleton + prototype) and doesn't know which one to inject into `ScopeDemoRunner`.

## Fixed ObjectFactory Section

**Update `ScopeDemoRunner.java` constructor:**

```java
@Component
public class ScopeDemoRunner implements CommandLineRunner {
    
    private final ApplicationContext context;
    private final NotificationService singletonBean;
    
    // ✅ FIXED: Use @Qualifier to resolve ambiguity
    @Autowired
    @Qualifier("prototypeNotificationService")
    private ObjectFactory<NotificationService> prototypeFactory;
    
    // ... rest unchanged
}
```

## Why This Happened (Bean Resolution Rules)

```
ScopeDemoRunner field prototypeFactory asks for: ObjectFactory<NotificationService>

Spring finds 2 matching beans:
├── singletonNotificationService (ScopeConfig)
└── prototypeNotificationService (ScopeConfig)  ← Ambiguous! ❌

Field injection FAILS without @Qualifier
```

## Complete Fixed Demo Runner

```java
@Component
public class ScopeDemoRunner implements CommandLineRunner {
    
    private final ApplicationContext context;
    private final NotificationService singletonBean;
    
    // FIXED: Explicitly specify prototype bean for ObjectFactory
    @Autowired
    @Qualifier("prototypeNotificationService")
    private ObjectFactory<NotificationService> prototypeFactory;
    
    public ScopeDemoRunner(
            ApplicationContext context,
            @Qualifier("singletonNotificationService") NotificationService singletonBean
    ) {
        this.context = context;
        this.singletonBean = singletonBean;
    }
    
    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === BEAN SCOPES DEMO ===");
        
        demoSingletonScope();
        demoPrototypeScope();
        demoObjectFactory();
        
        System.out.println("✅ Bean scopes demonstrated!");
        System.out.println("----------------------------------");
    }
    
    // ... other methods unchanged
}
```

## Expected Output (NOW Works ✅)

```
✅ Beans configured via Java Config!
----------------------------------

🚀 === BEAN SCOPES DEMO ===
🔢 Created CounterNotificationService #1

1️⃣ SINGLETON Scope (default):
singleton1.id = 1
singleton2.id = 1
✅ SAME instance (singleton)!

2️⃣ PROTOTYPE Scope:
🔢 Created CounterNotificationService #2
🔢 Created CounterNotificationService #3
proto1.id = 2
proto2.id = 3

3️⃣ ObjectFactory<Prototype> (for injection):
🔢 Created CounterNotificationService #4
🔢 Created CounterNotificationService #5
factory1.id = 4
factory2.id = 5
✅ NEW instances each factory.getObject()!

✅ Bean scopes demonstrated!
----------------------------------
```

## Key Fix Summary

| Problem | Solution |
|---------|----------|
| **Ambiguous injection** | `@Qualifier("specificBeanName")` |
| **Multiple matching types** | Explicit bean name reference |
| **ObjectFactory needs scope** | `@Qualifier("prototypeNotificationService")` |

## Bean Resolution Priority (Exam Tip)

```
1. @Qualifier("beanName") ← HIGHEST priority ✅
2. @Primary               ← Default fallback
3. Single matching bean   ← Auto-resolved
4. Multiple matches       ← FAIL ❌
```

**Quick fix:** Add `@Qualifier("prototypeNotificationService")` to the `ObjectFactory` field injection.

**Now `mvn spring-boot:run` will work perfectly!** All 3 sections (singleton → prototype → ObjectFactory) will demonstrate correctly. 🎯

**🎉 1.2 COMPLETE!** Java Configuration mastery achieved! 🚀
