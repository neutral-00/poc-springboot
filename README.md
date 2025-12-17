# 1.6.2 Implement and deploy Advices using Spring AOP

### Project Metadata

- Repository: https://github.com/neutral-00/poc-springboot
- **Parent Branch:** `main`
- **Branch:** `1.6.2-implement-and-deploy-advices-using-spring-aop`

---

## 🎯 Learning Objectives

- [ ] Enable Spring AOP in a Spring Boot application
- [ ] Create an Aspect class
- [ ] Implement different types of Advice
- [ ] Apply AOP to a service method
- [ ] Observe how Advice wraps method execution at runtime

---

## **Scenario**

Your team wants to add logging around service methods without modifying the service code.
This is a perfect use case for AOP:

- Log before a method runs
- Log after it completes
- Log exceptions
- Measure execution time

You will create:

- A service (`OrderService`)
- An aspect (`LoggingAspect`)
- Multiple advices (`@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`, `@Around`)

This will give your team a complete understanding of how Spring AOP works in practice.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.6.2-implement-and-deploy-advices-using-spring-aop
```

---

## **Step 2: Add the AOP starter (if not already present)**

In most Spring Boot apps, this is already included.
If not, add to `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

This enables proxy-based AOP.

---

## **Step 3: Create a service to be intercepted**

Create:

```
com.lousing.poc.aop.OrderService
```

```java
package com.lousing.poc.aop;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public String placeOrder(String item) {
        System.out.println("🛒 OrderService: Placing order for " + item);
        return "Order placed for " + item;
    }

    public void failOrder() {
        System.out.println("❌ OrderService: Simulating failure");
        throw new RuntimeException("Order failed due to system error");
    }
}
```

---

## **Step 4: Create an Aspect with multiple Advices**

Create:

```
com.lousing.poc.aop.LoggingAspect
```

```java
package com.lousing.poc.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.lousing.poc.aop.OrderService.placeOrder(..))")
    public void beforeAdvice(JoinPoint jp) {
        System.out.println("🔍 @Before: Calling method " + jp.getSignature().getName());
    }

    @After("execution(* com.lousing.poc.aop.OrderService.placeOrder(..))")
    public void afterAdvice(JoinPoint jp) {
        System.out.println("✅ @After: Completed method " + jp.getSignature().getName());
    }

    @AfterReturning(
            value = "execution(* com.lousing.poc.aop.OrderService.placeOrder(..))",
            returning = "result")
    public void afterReturningAdvice(Object result) {
        System.out.println("🎉 @AfterReturning: Method returned → " + result);
    }

    @AfterThrowing(
            value = "execution(* com.lousing.poc.aop.OrderService.failOrder(..))",
            throwing = "ex")
    public void afterThrowingAdvice(Exception ex) {
        System.out.println("💥 @AfterThrowing: Exception caught → " + ex.getMessage());
    }

    @Around("execution(* com.lousing.poc.aop.OrderService.placeOrder(..))")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("⏱️ @Around: Before execution");
        Object result = pjp.proceed();
        System.out.println("⏱️ @Around: After execution");
        return result;
    }
}
```

### ✅ What this demonstrates

You now have **five** types of advice:

| Advice Type       | When It Runs                               |
| ----------------- | ------------------------------------------ |
| `@Before`         | Before method execution                    |
| `@After`          | After method finishes (success or failure) |
| `@AfterReturning` | Only if method returns normally            |
| `@AfterThrowing`  | Only if method throws an exception         |
| `@Around`         | Wraps the entire method call               |

---

## **Step 5: Trigger the service in your main class**

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ AOP Advice Demo Ready!");

        var service = context.getBean(com.lousing.poc.aop.OrderService.class);

        System.out.println("\n--- Successful Order ---");
        service.placeOrder("Laptop");

        System.out.println("\n--- Failed Order ---");
        try {
            service.failOrder();
        } catch (Exception ignored) {}

        System.out.println("----------------------------------");
    }
}
```

---

## **Step 6: Run the application**

```bash
mvn spring-boot:run
```

Expected output (simplified):

```
✅ AOP Advice Demo Ready!

--- Successful Order ---
⏱️ @Around: Before execution
🔍 @Before: Calling method placeOrder
🛒 OrderService: Placing order for Laptop
🎉 @AfterReturning: Method returned → Order placed for Laptop
✅ @After: Completed method placeOrder
⏱️ @Around: After execution

--- Failed Order ---
❌ OrderService: Simulating failure
💥 @AfterThrowing: Exception caught → Order failed due to system error
----------------------------------
```

---

# ✅ Summary

In this tutorial, you learned:

- How to enable AOP in Spring Boot
- How to create an Aspect class
- How to implement all major types of Advice
- How to intercept service methods at runtime
- How Spring uses proxies to apply AOP behavior

This sets you up perfectly for the next tutorial:

✅ **1.6.3 Use AOP Pointcut Expressions**
