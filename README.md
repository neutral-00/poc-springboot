# 1.4.4 Explain and use “Stereotype” Annotations

### Project Metadata
- Repository: https://github.com/neutral-00/poc-springboot
- **Parent Branch:** `main`
- **Branch:** `1.4.4-explain-and-use-stereotype-annotations`

---

## 🎯 Learning Objectives
- [ ] Understand what stereotype annotations are
- [ ] Use `@Component`, `@Service`, `@Repository`, and `@Controller`
- [ ] Learn how stereotype annotations enable component scanning
- [ ] Understand when to choose each stereotype
- [ ] Observe how Spring registers beans automatically

---

## **Scenario**
Your team is transitioning from manual `@Bean` definitions to component scanning.  
They want to understand how Spring automatically discovers and registers beans using stereotype annotations.

You will create:
- A `@Service` class
- A `@Repository` class
- A `@Component` utility
- A simple runner that retrieves and uses these beans

This tutorial demonstrates how Spring Boot’s auto-scanning works and when to use each stereotype.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.4.4-explain-and-use-stereotype-annotations
```

---

## **Step 2: Create a @Repository class**

Create:

```
com.lousing.poc.repositories.MessageRepository
```

```java
package com.lousing.poc.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {

    public String fetchMessage() {
        return "Message fetched from repository";
    }
}
```

### ✅ Why `@Repository`?
- Indicates data-access logic
- Enables exception translation (Spring converts DB exceptions into DataAccessException hierarchy)
- Helps organize your architecture

---

## **Step 3: Create a @Service class**

Create:

```
com.lousing.poc.services.MessageService
```

```java
package com.lousing.poc.services;

import com.lousing.poc.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository repo;

    public MessageService(MessageRepository repo) {
        this.repo = repo;
    }

    public String processMessage() {
        return repo.fetchMessage() + " | processed by service";
    }
}
```

### ✅ Why `@Service`?
- Represents business logic
- Makes intent clear
- Helps future developers quickly understand the role of the class

---

## **Step 4: Create a @Component utility**

Create:

```
com.lousing.poc.util.TimestampUtil
```

```java
package com.lousing.poc.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimestampUtil {

    public String now() {
        return LocalDateTime.now().toString();
    }
}
```

### ✅ Why `@Component`?
- Generic stereotype
- Use when the class doesn’t fit `@Service`, `@Repository`, or `@Controller`
- Still participates in component scanning

---

## **Step 5: Use the beans in your main application**

Modify your main class:

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ Stereotype Annotation Demo Ready!");

        var service = context.getBean(com.lousing.poc.services.MessageService.class);
        var util = context.getBean(com.lousing.poc.util.TimestampUtil.class);

        System.out.println(service.processMessage());
        System.out.println("Timestamp: " + util.now());

        System.out.println("----------------------------------");
    }
}
```

---

## **Step 6: Run the application**

```bash
mvn spring-boot:run
```

Expected output:

```
✅ Stereotype Annotation Demo Ready!
Message fetched from repository | processed by service
Timestamp: 2025-01-15T10:23:45.123
----------------------------------
```

---

## ✅ Understanding Stereotype Annotations

### **1. @Component**
- Generic Spring-managed bean
- Use when no other stereotype fits

### **2. @Service**
- Business logic layer
- Helps readability and architecture clarity

### **3. @Repository**
- Data access layer
- Enables exception translation

### **4. @Controller / @RestController**
- Web layer
- Handles HTTP requests

---

## ✅ Summary

In this tutorial, you learned:

- What stereotype annotations are
- How Spring uses them for component scanning
- When to use `@Component`, `@Service`, and `@Repository`
- How Spring automatically registers beans without `@Bean` methods
- How to structure your application using stereotypes

This completes **Objective 1.4** — Annotation-Based Configuration and Component Scanning.

Next up is **Objective 1.5 Spring Bean Lifecycle**, starting with:

✅ **1.5.1 Explain the Spring Bean Lifecycle**

