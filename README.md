# 1.4.1 Explain and use Annotation-based Configuration

### Project Metadata
- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `main`
- **Branch:** `1.4.1-explain-and-use-annotation-based-configuration`

---

## 🎯 Learning Objectives
- [ ] Understand what annotation-based configuration is in Spring
- [ ] Replace XML configuration with annotations
- [ ] Create beans using `@Configuration` and `@Bean`
- [ ] Retrieve and use annotated beans from the Spring container

---

## **Scenario**
Your team is modernizing an old Spring application that still uses XML (`applicationContext.xml`) for bean definitions.  
You’ve been asked to demonstrate how annotation-based configuration works so the team can migrate away from XML.

You will create:
- A `@Configuration` class
- A couple of beans defined using `@Bean`
- A small runner that retrieves and uses these beans

This tutorial builds the foundation for later topics like component scanning, lifecycle hooks, and AOP.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**
From your repo root:

```bash
git checkout main
git pull
git checkout -b 1.4.1-explain-and-use-annotation-based-configuration
```

---

## **Step 2: Create a simple service class**

Create a package:

```
com.lousing.poc.services
```

Add a class:

```java
package com.lousing.poc.services;

public class GreetingService {

    public String greet(String name) {
        return "Hello, " + name + "!";
    }
}
```

This class is *not* annotated yet — we will register it manually using annotation-based configuration.

---

## **Step 3: Create a @Configuration class**

Create:

```
com.lousing.poc.config.AppConfig
```

```java
package com.lousing.poc.config;

import com.lousing.poc.services.GreetingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public GreetingService greetingService() {
        return new GreetingService();
    }
}
```

### ✅ What’s happening here?

- `@Configuration` tells Spring this class contains bean definitions.
- `@Bean` tells Spring to register the returned object as a bean in the ApplicationContext.
- The method name (`greetingService`) becomes the bean ID.

This is the annotation-based equivalent of:

```xml
<bean id="greetingService" class="com.lousing.poc.services.GreetingService"/>
```

---

## **Step 4: Import the configuration into your Spring Boot app**

Modify your main class:

```java
@SpringBootApplication
@Import(AppConfig.class)
public class PocSpringbootApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("✅ Annotation Config Demo Ready!");
        System.out.println("----------------------------------");

        GreetingService service = context.getBean(GreetingService.class);
        System.out.println(service.greet("Annotation-Based Config"));
    }
}
```

### ✅ Why `@Import`?

- Spring Boot auto-scans components, but **@Configuration classes outside the main package hierarchy must be imported**.
- This keeps your tutorial explicit and educational.

---

## **Step 5: Run the application**

```bash
mvn spring-boot:run
```

Expected output:

```
✅ Annotation Config Demo Ready!
----------------------------------
Hello, Annotation-Based Config!
```

---

## ✅ Summary

By the end of this tutorial, you:

- Created a bean using `@Configuration` + `@Bean`
- Loaded it into the Spring container
- Retrieved and used it in your main application
- Understood how annotation-based configuration replaces XML

This sets the stage for:
- Component scanning
- Stereotype annotations
- Lifecycle callbacks
- AOP

---

If you want, I can now generate **1.4.2 Discuss Best Practices for Configuration Choices** in the same format.
