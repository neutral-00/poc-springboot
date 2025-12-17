# 1.5.5 Avoid issues when injecting beans by type

### Project Metadata

- Repository: https://github.com/neutral-00/poc-springboot
- **Parent Branch:** `main`
- **Branch:** `1.5.5-avoid-issues-when-injecting-beans-by-type`

---

## 🎯 Learning Objectives

- [ ] Understand how Spring resolves beans when injecting by type
- [ ] Learn why ambiguity occurs when multiple beans share the same type
- [ ] Use `@Primary`, `@Qualifier`, and bean names to resolve conflicts
- [ ] Understand best practices for avoiding injection ambiguity
- [ ] Observe real examples of injection failures and fixes

---

## **Scenario**

Your team is adding new features and ends up with multiple beans of the same type.
Suddenly, Spring starts throwing:

```
NoUniqueBeanDefinitionException
```

This happens when Spring tries to inject a bean **by type**, but multiple candidates match.

In this tutorial, you will:

- Create multiple beans of the same type
- Trigger an injection conflict
- Fix it using `@Primary` and `@Qualifier`
- Learn best practices to avoid these issues

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.5.5-avoid-issues-when-injecting-beans-by-type
```

---

## **Step 2: Create a common interface**

Create:

```
com.lousing.poc.payments.PaymentProcessor
```

```java
package com.lousing.poc.payments;

public interface PaymentProcessor {
    String process();
}
```

---

## **Step 3: Create two implementations**

### **CreditCardProcessor**

```java
package com.lousing.poc.payments;

import org.springframework.stereotype.Component;

@Component
public class CreditCardProcessor implements PaymentProcessor {

    @Override
    public String process() {
        return "💳 Processing credit card payment";
    }
}
```

### **PaypalProcessor**

```java
package com.lousing.poc.payments;

import org.springframework.stereotype.Component;

@Component
public class PaypalProcessor implements PaymentProcessor {

    @Override
    public String process() {
        return "🅿️ Processing PayPal payment";
    }
}
```

Now Spring has **two beans of type `PaymentProcessor`**, which will cause ambiguity.

---

## **Step 4: Create a service that injects PaymentProcessor**

Create:

```
com.lousing.poc.payments.PaymentService
```

```java
package com.lousing.poc.payments;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentProcessor paymentProcessor;

    public PaymentService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public String makePayment() {
        return paymentProcessor.process();
    }
}
```

### ✅ What happens now?

Spring tries to inject `PaymentProcessor` but finds **two candidates**:

- creditCardProcessor
- paypalProcessor

This triggers:

```
NoUniqueBeanDefinitionException
```

Perfect — now we can fix it.

---

## **Step 5: Fix #1 — Use @Primary**

Mark one implementation as the default:

```java
@Component
@Primary
public class CreditCardProcessor implements PaymentProcessor {
    //...
}
```

Now Spring will inject `CreditCardProcessor` unless told otherwise.

---

## **Step 6: Fix #2 — Use @Qualifier**

If you want to explicitly choose PayPal:

Update `PaymentService`:

```java
public PaymentService(@Qualifier("paypalProcessor") PaymentProcessor processor) {
    this.processor = processor;
}
```

Or use field injection (not recommended, but common in legacy apps):

```java
@Qualifier("paypalProcessor")
@Autowired
private PaymentProcessor processor;
```

---

## **Step 7: Trigger the service in your main class**

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ Bean Injection by Type Demo Ready!");

        var service = context.getBean(com.lousing.poc.payments.PaymentService.class);
        service.makePayment();

        System.out.println("----------------------------------");
    }
}
```

---

## **Step 8: Run the application**

```bash
mvn spring-boot:run
```

Expected output (if `@Primary` is on CreditCardProcessor):

```
✅ Bean Injection by Type Demo Ready!
💳 Processing credit card payment
----------------------------------
```

If using `@Qualifier("paypalProcessor")`:

```
🅿️ Processing PayPal payment
```

---

# ✅ Best Practices for Avoiding Bean Injection Issues

### ✅ 1. Prefer constructor injection

It makes dependencies explicit and avoids hidden ambiguity.

### ✅ 2. Use `@Primary` for the most common implementation

Great for “default” strategies.

### ✅ 3. Use `@Qualifier` when multiple beans of the same type exist

This is the cleanest and most explicit approach.

### ✅ 4. Avoid field injection

Harder to test, harder to detect ambiguity.

### ✅ 5. Use meaningful bean names

Spring uses class names by default, but you can override:

```java
@Component("fastProcessor")
```

### ✅ 6. Avoid having multiple beans of the same type unless necessary

Strategy pattern? Fine.
Accidental duplication? Avoid.

---

# ✅ Summary

In this tutorial, you learned:

- Why injecting beans by type can cause ambiguity
- How Spring resolves bean candidates
- How to fix conflicts using `@Primary` and `@Qualifier`
- Best practices for avoiding injection issues
- How to observe and control which bean gets injected

This completes **Objective 1.5 — Spring Bean Lifecycle**.

Next up is **Objective 1.6 Aspect Oriented Programming**, starting with:

✅ **1.6.1 Explain the concepts behind AOP and the problems that it solves**
