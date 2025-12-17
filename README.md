# 1.6.4 Explain different types of Advice and when to use them

### Project Metadata

- Repository: https://github.com/neutral-00/poc-springboot
- **Parent Branch:** `main`
- **Branch:** `1.6.4-explain-different-types-of-advice-and-when-to-use-them`

---

## 🎯 Learning Objectives

- [ ] Understand all five types of AOP advice in Spring
- [ ] Learn when each advice type is appropriate
- [ ] See practical examples of each advice
- [ ] Understand how advice types affect method execution flow
- [ ] Build intuition for choosing the right advice for the right scenario

---

## **Scenario**

Your team is now comfortable with AOP and wants to apply it effectively.
However, they’re unsure:

- When to use `@Before` vs `@Around`
- When `@AfterReturning` is better than `@After`
- When `@AfterThrowing` is essential
- Why `@Around` is the most powerful (and most dangerous) advice

This tutorial explains each advice type, shows when to use it, and provides runnable examples.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.6.4-explain-different-types-of-advice-and-when-to-use-them
```

---

## **Step 2: Create a demo service with multiple behaviors**

Create:

```
com.lousing.poc.aop3.TaskService
```

```java
package com.lousing.poc.aop3;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    public void startTask() {
        System.out.println("🚀 Task started");
    }

    public String completeTask() {
        System.out.println("✅ Task completed");
        return "Task Result";
    }

    public void failTask() {
        System.out.println("💥 Task failed");
        throw new RuntimeException("Simulated task failure");
    }
}
```

This gives us three different execution paths to test each advice type.

---

## **Step 3: Create an Aspect demonstrating all advice types**

Create:

```
com.lousing.poc.aop3.AdviceDemoAspect
```

```java
package com.lousing.poc.aop3;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdviceDemoAspect {

    // 1️⃣ BEFORE ADVICE
    @Before("execution(* com.lousing.poc.aop3.TaskService.startTask(..))")
    public void beforeAdvice(JoinPoint jp) {
        System.out.println("🔍 @Before: Preparing to run " + jp.getSignature().getName());
    }

    // 2️⃣ AFTER ADVICE (runs on success OR failure)
    @After("execution(* com.lousing.poc.aop3.TaskService.startTask(..))")
    public void afterAdvice(JoinPoint jp) {
        System.out.println("📌 @After: Finished running " + jp.getSignature().getName());
    }

    // 3️⃣ AFTER RETURNING ADVICE
    @AfterReturning(
            value = "execution(* com.lousing.poc.aop3.TaskService.completeTask(..))",
            returning = "result")
    public void afterReturningAdvice(Object result) {
        System.out.println("🎉 @AfterReturning: Method returned → " + result);
    }

    // 4️⃣ AFTER THROWING ADVICE
    @AfterThrowing(
            value = "execution(* com.lousing.poc.aop3.TaskService.failTask(..))",
            throwing = "ex")
    public void afterThrowingAdvice(Exception ex) {
        System.out.println("💣 @AfterThrowing: Exception caught → " + ex.getMessage());
    }

    // 5️⃣ AROUND ADVICE
    @Around("execution(* com.lousing.poc.aop3.TaskService.completeTask(..))")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("⏱️ @Around: Before execution");
        Object result = pjp.proceed();
        System.out.println("⏱️ @Around: After execution");
        return result;
    }
}
```

---

## **Step 4: Trigger the service in your main class**

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ AOP Advice Types Demo Ready!");

        var service = context.getBean(com.lousing.poc.aop3.TaskService.class);

        System.out.println("\n--- startTask() ---");
        service.startTask();

        System.out.println("\n--- completeTask() ---");
        System.out.println(service.completeTask());

        System.out.println("\n--- failTask() ---");
        try {
            service.failTask();
        } catch (Exception ignored) {}

        System.out.println("----------------------------------");
    }
}
```

---

## **Step 5: Run the application**

```bash
mvn spring-boot:run
```

Expected output (simplified):

```
✅ AOP Advice Types Demo Ready!

--- startTask() ---
🔍 @Before: Preparing to run startTask
🚀 Task started
📌 @After: Finished running startTask

--- completeTask() ---
⏱️ @Around: Before execution
✅ Task completed
🎉 @AfterReturning: Method returned → Task Result
⏱️ @Around: After execution
Task Result

--- failTask() ---
💥 Task failed
💣 @AfterThrowing: Exception caught → Simulated task failure
----------------------------------
```

---

# ✅ When to Use Each Advice Type

### ✅ **1. @Before**

Use when you need to:

- Validate input
- Check authentication
- Log method entry
- Start timers

Runs **before** the method executes.

---

### ✅ **2. @After**

Use when you need to:

- Clean up resources
- Log method exit
- Always run logic (success or failure)

Runs **after** the method finishes (even if it throws).

---

### ✅ **3. @AfterReturning**

Use when you need to:

- Log returned values
- Transform results
- Trigger events after success

Runs **only if the method returns normally**.

---

### ✅ **4. @AfterThrowing**

Use when you need to:

- Log exceptions
- Trigger alerts
- Wrap exceptions into custom ones

Runs **only if the method throws**.

---

### ✅ **5. @Around**

Use when you need to:

- Measure execution time
- Wrap method calls
- Modify arguments
- Modify return values
- Completely replace method behavior

This is the **most powerful** advice type — but also the easiest to misuse.

---

# ✅ Summary

In this tutorial, you learned:

- All five types of AOP advice
- When each advice type is appropriate
- How advice affects method execution flow
- How to implement each advice in Spring
- How to observe advice behavior in a real application

This completes **Objective 1.6 — Aspect Oriented Programming**.
