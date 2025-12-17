# 1.4.3 Use @PostConstruct and @PreDestroy

### Project Metadata
- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `main`
- **Branch:** `1.4.3-use-postconstruct-and-predestroy`

---

## 🎯 Learning Objectives
- [ ] Understand what `@PostConstruct` and `@PreDestroy` do
- [ ] Add lifecycle callbacks to Spring-managed beans
- [ ] Observe initialization and destruction behavior in a running Spring Boot application
- [ ] Understand when to use these annotations and when not to

---

## **Scenario**
Your team wants certain beans to perform setup work after dependency injection is complete (e.g., loading caches, validating configuration) and cleanup work before the application shuts down (e.g., closing connections, flushing buffers).

Spring provides two lifecycle annotations:
- `@PostConstruct` → runs **after** the bean is created and dependencies are injected
- `@PreDestroy` → runs **before** the bean is destroyed

You will create a bean that logs initialization and cleanup steps so the team can clearly see how these lifecycle hooks behave.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.4.3-use-postconstruct-and-predestroy
```

---

## **Step 2: Create a bean that uses @PostConstruct and @PreDestroy**

Create:

```
com.lousing.poc.services.LifecycleService
```

```java
package com.lousing.poc.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class LifecycleService {
    public LifecycleService() {
        System.out.println("\n➡️ LifecycleService: Constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ LifecycleService: @PostConstruct initialization logic executed\n");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("🧹 LifecycleService: @PreDestroy cleanup logic executed");
    }
}
```

### ✅ What’s happening here?

- The constructor runs **first** when the bean is created.
- `@PostConstruct` runs **after** Spring injects dependencies.
- `@PreDestroy` runs **when the application context is shutting down**.

---

## **Step 3: Trigger bean creation in the main application**

Modify your main class to retrieve the bean:

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ Lifecycle Demo Ready!");

        // Force bean retrieval so logs appear immediately
        context.getBean(LifecycleService.class);

        System.out.println("----------------------------------");
    }
}
```

Spring would create the bean anyway, but retrieving it ensures the logs appear right after startup.

---

## **Step 4: Run the application**

```bash
mvn spring-boot:run
```

Expected output:

```
➡️ LifecycleService: Constructor called
✅ LifecycleService: @PostConstruct initialization logic executed

✅ Lifecycle Demo Ready!
----------------------------------
```

When you stop the application (Ctrl+C):

```
🧹 LifecycleService: @PreDestroy cleanup logic executed
```

---

## ✅ Step 5: Understand when to use these annotations

### ✅ Use `@PostConstruct` for:
- Loading configuration from a file
- Initializing caches
- Validating injected dependencies
- Starting scheduled tasks

### ✅ Use `@PreDestroy` for:
- Closing database connections
- Stopping background threads
- Flushing logs or buffers
- Releasing external resources

---

## ✅ Step 6: When NOT to use them

### ❌ Avoid in:
- Prototype-scoped beans (cleanup won’t run)
- Complex initialization logic (prefer `InitializingBean` or custom init methods)
- Beans managed outside Spring (annotations won’t work)

### ❌ Avoid heavy work in @PostConstruct
It slows down application startup.

---

## ✅ Summary

In this tutorial, you learned:

- How `@PostConstruct` and `@PreDestroy` work
- How Spring manages bean lifecycle callbacks
- When to use these annotations
- When to avoid them
- How to observe initialization and cleanup behavior in a real Spring Boot app

This prepares you for the next topic: **stereotype annotations**, which build on component scanning and bean lifecycle concepts.

---

Ready for **1.4.4 Explain and use “Stereotype” Annotations**?
