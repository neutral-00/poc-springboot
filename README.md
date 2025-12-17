# 1.5.1 Explain the Spring Bean Lifecycle

### Project Metadata
- Repository: https://github.com/neutral-00/poc-springboot
- **Parent Branch:** `main`
- **Branch:** `1.5.1-explain-spring-bean-lifecycle`

---

## 🎯 Learning Objectives
- [ ] Understand each phase of the Spring Bean lifecycle
- [ ] Observe lifecycle events in a running Spring Boot application
- [ ] Learn how Spring manages bean creation, initialization, and destruction
- [ ] Understand where custom logic can be inserted into the lifecycle

---

## **Scenario**
Your team wants to understand *exactly* how Spring manages beans behind the scenes.  
This includes:

- How beans are instantiated
- How dependencies are injected
- How initialization callbacks work
- How destruction callbacks work
- How the ApplicationContext controls everything

You will create a bean that logs each lifecycle phase so the team can visually understand the order of events.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.5.1-explain-spring-bean-lifecycle
```

---

## **Step 2: Create a bean that logs lifecycle events**

Create:

```
com.lousing.poc.lifecycle.LifecycleLogger
```

```java
package com.lousing.poc.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class LifecycleLogger implements InitializingBean, DisposableBean {

    public LifecycleLogger() {
        System.out.println("➡️ Constructor: Bean instance created");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("✅ @PostConstruct: Dependencies injected, bean initialized");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("🔧 InitializingBean.afterPropertiesSet(): Additional initialization logic");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("🧹 @PreDestroy: Cleanup before bean destruction");
    }

    @Override
    public void destroy() {
        System.out.println("🗑️ DisposableBean.destroy(): Final cleanup logic");
    }
}
```

### ✅ What this demonstrates

You now have **five lifecycle touchpoints**:

| Phase | Trigger | Method |
|------|---------|--------|
| Instantiation | Spring creates the bean | Constructor |
| Dependency Injection | After wiring dependencies | `@PostConstruct` |
| Initialization | After PostConstruct | `afterPropertiesSet()` |
| Shutdown Prep | Before context closes | `@PreDestroy` |
| Final Cleanup | After PreDestroy | `destroy()` |

This gives a complete picture of the lifecycle.

---

## **Step 3: Trigger bean creation in your main class**

Modify your main class:

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ Bean Lifecycle Demo Ready!");

        // Force bean creation logs to appear immediately
        context.getBean(com.lousing.poc.lifecycle.LifecycleLogger.class);

        System.out.println("----------------------------------");
    }
}
```

---

## **Step 4: Run the application**

```bash
mvn spring-boot:run
```

Expected output:

```
➡️ Constructor: Bean instance created
✅ @PostConstruct: Dependencies injected, bean initialized
🔧 InitializingBean.afterPropertiesSet(): Additional initialization logic

✅ Bean Lifecycle Demo Ready!
----------------------------------
```

When you stop the application:

```
🧹 @PreDestroy: Cleanup before bean destruction
🗑️ DisposableBean.destroy(): Final cleanup logic
```

---

## ✅ Step 5: Understand the Full Spring Bean Lifecycle

Spring’s lifecycle can be summarized in **eight phases**:

### **1. Bean Definition Loading**
Spring reads classpath, annotations, and configuration classes.

### **2. Bean Instantiation**
Spring calls the constructor.

### **3. Dependency Injection**
Spring injects fields, constructors, and setters.

### **4. `@PostConstruct`**
Runs after dependencies are injected.

### **5. `InitializingBean.afterPropertiesSet()`**
Optional initialization hook.

### **6. Custom init-method (if configured)**
Defined via `@Bean(initMethod = "...")`.

### **7. Bean Ready for Use**
Application can now use the bean.

### **8. Shutdown Phase**
When the context closes:
- `@PreDestroy`
- `DisposableBean.destroy()`
- Custom destroy-method

---

## ✅ Step 6: When to use each lifecycle hook

### ✅ Use `@PostConstruct`
For simple initialization logic.

### ✅ Use `InitializingBean.afterPropertiesSet()`
For framework-level or library-style initialization.

### ✅ Use custom init/destroy methods
When you want to avoid annotations or interfaces.

### ✅ Use `@PreDestroy`
For cleanup logic.

---

## ✅ Summary

In this tutorial, you learned:

- The complete Spring Bean lifecycle
- How Spring instantiates, wires, initializes, and destroys beans
- How to hook into lifecycle events using annotations and interfaces
- How to observe lifecycle logs in a real Spring Boot application

This sets the foundation for the next tutorial:

✅ **1.5.2 Use a BeanFactoryPostProcessor and a BeanPostProcessor**
