# 1.4.2 Discuss Best Practices for Configuration Choices

### Project Metadata
- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `main`
- **Branch:** `1.4.2-best-practices-for-configuration-choices`

---

## 🎯 Learning Objectives
- [ ] Understand when to use `@Configuration` vs component scanning
- [ ] Know when to use Java-based config vs autoconfiguration
- [ ] Learn best practices for organizing configuration classes
- [ ] Avoid common pitfalls in annotation-based configuration

---

## **Scenario**
Your team is expanding the application and wants to ensure that configuration choices remain clean, maintainable, and scalable.  
You’ve been asked to document and demonstrate **best practices** for annotation-based configuration so the team can follow a consistent approach.

This tutorial is conceptual but includes small code examples to reinforce the ideas.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.4.2-best-practices-for-configuration-choices
```

---

## **Step 2: Understand the three main configuration styles**

Spring supports multiple configuration approaches:

### **1. Java-based configuration (`@Configuration` + `@Bean`)**
✅ Best when:
- You need fine-grained control
- You want explicit bean creation
- You configure third‑party classes (not annotated)

Example:

```java
@Configuration
public class PaymentConfig {

    @Bean
    public PaymentGateway gateway() {
        return new PaymentGateway("sandbox-key");
    }
}
```

---

### **2. Component scanning (`@Component`, `@Service`, `@Repository`, `@Controller`)**
✅ Best when:
- You control the source code
- The class represents a domain concept (service, repo, controller)
- You want minimal boilerplate

Example:

```java
@Service
public class OrderService { }
```

---

### **3. Auto-configuration (Spring Boot magic)**
✅ Best when:
- You want convention over configuration
- You rely on Spring Boot starters
- You want minimal setup for common frameworks (JPA, Web, Security)

Example:  
You add `spring-boot-starter-web` → Spring auto-configures Tomcat, Jackson, MVC, etc.

---

## **Step 3: Best Practices for Choosing Configuration Styles**

### ✅ **1. Prefer component scanning for application-level classes**
Use stereotype annotations (`@Service`, `@Component`, etc.) for your own code.

Why:
- Cleaner
- Less boilerplate
- Plays well with Spring Boot’s auto-scanning

---

### ✅ **2. Use `@Configuration` for infrastructure-level beans**
Examples:
- Third-party library objects
- Custom serializers
- ObjectMapper customization
- Thread pools
- Security filters

This keeps infrastructure separate from business logic.

---

### ✅ **3. Group configuration classes by domain**
Instead of dumping everything into one `AppConfig`, create focused configs:

```
config/
  WebConfig.java
  SecurityConfig.java
  PaymentConfig.java
  MessagingConfig.java
```

This improves readability and reduces merge conflicts.

---

### ✅ **4. Avoid mixing component scanning and manual `@Bean` definitions for the same class**
Bad:

```java
@Component
public class EmailService { }

@Configuration
public class AppConfig {
    @Bean
    public EmailService emailService() { return new EmailService(); }
}
```

This creates **duplicate beans** and causes ambiguity.

---

### ✅ **5. Prefer constructor injection**
Constructor injection:
- Makes dependencies explicit
- Enables immutability
- Works better with testing
- Avoids circular dependency issues

Example:

```java
@Service
public class ReportService {

    private final DataService dataService;

    public ReportService(DataService dataService) {
        this.dataService = dataService;
    }
}
```

---

### ✅ **6. Keep configuration classes stateless**
Never store mutable state inside `@Configuration` classes.

Bad:

```java
@Configuration
public class BadConfig {
    private int counter = 0;
}
```

Configuration classes may be proxied — state can behave unpredictably.

---

## **Step 4: Add a small demo config to reinforce the concepts**

Create:

```
com.lousing.poc.config.InfrastructureConfig
```

```java
package com.lousing.poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfrastructureConfig {

    @Bean
    public String appVersion() {
        return "1.0.0";
    }
}
```

Use it in your main class:

```java
package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);
        String appVersion = context.getBean("appVersion", String.class);
        System.out.println("\nApplication Version: " + appVersion);
        System.out.println("--------------------------\n");
    }
}
```

This demonstrates:
- Infrastructure-level config
- Explicit bean creation
- Clean separation from business logic

---

## ✅ Summary

In this tutorial, you learned:

- When to use `@Configuration` vs component scanning
- How to structure configuration classes
- How to avoid common pitfalls
- Why constructor injection is preferred
- How to keep configuration clean and maintainable

These best practices will help you build scalable Spring applications and prepare you for upcoming topics like lifecycle callbacks and AOP.

---
