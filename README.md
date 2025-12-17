# 1.5.4 Describe how Spring determines bean creation order

### Project Metadata

- Repository: https://github.com/neutral-00/poc-springboot
- **Parent Branch:** `main`
- **Branch:** `1.5.4-describe-how-spring-determines-bean-creation-order`

---

## 🎯 Learning Objectives

- [ ] Understand how Spring decides the order in which beans are created
- [ ] Learn the difference between dependency-driven ordering and annotation-driven ordering
- [ ] Use `@DependsOn` to explicitly control creation order
- [ ] Observe bean creation logs to understand the lifecycle
- [ ] Understand when bean creation order matters (and when it doesn’t)

---

## **Scenario**

Your team is debugging an issue where certain beans must be initialized before others.  
They want to understand:

- How Spring decides which bean to create first
- How dependencies influence creation order
- How to override the default behavior
- When to use `@DependsOn`

You will create three beans and observe how Spring determines their creation order.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.5.4-describe-how-spring-determines-bean-creation-order
```

---

## **Step 2: Create three beans with logging**

Create:

```
com.lousing.poc.order.BeanA
com.lousing.poc.order.BeanB
com.lousing.poc.order.BeanC
```

### **BeanA**

```java
package com.lousing.poc.order;

import org.springframework.stereotype.Component;

@Component
public class BeanA {

    public BeanA() {
        System.out.println("✅ BeanA created");
    }
}
```

### **BeanB**

```java
package com.lousing.poc.order;

import org.springframework.stereotype.Component;

@Component
public class BeanB {

    public BeanB() {
        System.out.println("✅ BeanB created");
    }
}
```

### **BeanC**

```java
package com.lousing.poc.order;

import org.springframework.stereotype.Component;

@Component
public class BeanC {

    public BeanC() {
        System.out.println("✅ BeanC created");
    }
}
```

At this point, Spring is free to create them in **any order**, because:

- They have no dependencies
- They have no explicit ordering annotations

---

## **Step 3: Add dependencies to influence creation order**

Update **BeanB** to depend on **BeanA**:

```java
@Component
public class BeanB {

    private final BeanA beanA;

    public BeanB(BeanA beanA) {
        this.beanA = beanA;
        System.out.println("✅ BeanB created (depends on BeanA)");
    }
}
```

Update **BeanC** to depend on **BeanB**:

```java
@Component
public class BeanC {

    private final BeanB beanB;

    public BeanC(BeanB beanB) {
        this.beanB = beanB;
        System.out.println("✅ BeanC created (depends on BeanB)");
    }
}
```

### ✅ Now the creation order is deterministic:

1. BeanA
2. BeanB
3. BeanC

Because Spring always creates dependencies **before** dependents.

---

## **Step 4: Use @DependsOn to enforce explicit ordering**

Let’s force **BeanA** to be created _after_ BeanC (even though it makes no logical sense — purely for demonstration).

Update BeanA:

```java
@Component
@DependsOn("beanC")
public class BeanA {

    public BeanA() {
        System.out.println("✅ BeanA created (forced after BeanC)");
    }
}
```

### ✅ What happens now?

Spring will try to create the beans in this order:

1. Create BeanC but it depends on BeanB
2. So try to create BeanB but it depends on BeanA
3. So try to create BeanA but it depends on BeanC so we have a circular dependency!

Even though BeanB depends on BeanA, Spring will still honor `@DependsOn` and try to reorder creation.
But in our case, it leads to a circular dependency error.

This demonstrates how powerful — and dangerous — `@DependsOn` can be.
As a fix, you can remove the `@DependsOn` annotation to restore logical ordering.

---

## **Step 5: Trigger bean creation in your main class**

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ Bean Creation Order Demo Ready!");
        System.out.println("----------------------------------");
    }
}
```

Spring will create all beans automatically at startup.

---

## **Step 6: Run the application**

```bash
mvn spring-boot:run
```

Expected output (order may vary depending on your @DependsOn setup):

```
✅ BeanA created
✅ BeanB created (depends on BeanA)
✅ BeanC created (depends on BeanB)

✅ Bean Creation Order Demo Ready!
----------------------------------
```

---

# ✅ How Spring Determines Bean Creation Order

### ✅ 1. **Dependency Graph (Primary Rule)**

Spring always creates beans in the order required to satisfy constructor dependencies.

### ✅ 2. **@DependsOn (Explicit Ordering)**

Overrides dependency-based ordering.

### ✅ 3. **Lazy vs Eager Initialization**

- Singleton beans are created eagerly at startup
- Lazy beans (`@Lazy`) are created only when requested

### ✅ 4. **BeanFactoryPostProcessor and BeanPostProcessor**

These run before and after bean creation but do **not** change creation order.

### ✅ 5. **No Guaranteed Order Without Dependencies**

If beans have no dependencies and no `@DependsOn`, Spring may create them in any order.

---

# ✅ Summary

In this tutorial, you learned:

- How Spring determines bean creation order
- How constructor injection influences ordering
- How `@DependsOn` can override the default behavior
- How to observe creation order in logs
- Why explicit ordering should be used sparingly

This prepares you for the final topic in Objective 1.5:

✅ **1.5.5 Avoid issues when injecting beans by type**
