# 1.6.3 Use AOP Pointcut Expressions

### Project Metadata

- Repository: https://github.com/neutral-00/poc-springboot
- **Parent Branch:** `main`
- **Branch:** `1.6.3-use-aop-pointcut-expressions`

---

## 🎯 Learning Objectives

- [ ] Understand what a pointcut expression is
- [ ] Learn the most common Spring AOP pointcut patterns
- [ ] Apply pointcuts to match packages, classes, and method signatures
- [ ] Reuse pointcuts using `@Pointcut` methods
- [ ] Observe how pointcuts control where advice is applied

---

## **Scenario**

Your team wants to apply logging to:

- All service methods
- Only methods in a specific package
- Only methods with certain names
- Only methods with certain arguments

Instead of writing separate advices for each method, you can use **pointcut expressions** to precisely target join points.

This tutorial shows how to write and reuse pointcuts in Spring AOP.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.6.3-use-aop-pointcut-expressions
```

---

## **Step 2: Create a demo service with multiple methods**

Create:

```
com.lousing.poc.aop2.ProductService
```

```java
package com.lousing.poc.aop2;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public void addProduct(String name) {
        System.out.println("📦 Adding product: " + name);
    }

    public void deleteProduct(int id) {
        System.out.println("🗑️ Deleting product with ID: " + id);
    }

    public String findProduct(int id) {
        return "Product-" + id;
    }
}
```

This gives us multiple method signatures to target with pointcuts.

---

## **Step 3: Create an Aspect with reusable pointcuts**

Create:

```
com.lousing.poc.aop2.PointcutDemoAspect
```

```java
package com.lousing.poc.aop2;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointcutDemoAspect {

    // 1️⃣ Match all methods in ProductService
    @Pointcut("execution(* com.lousing.poc.aop2.ProductService.*(..))")
    public void allProductMethods() {}

    // 2️⃣ Match only methods starting with 'add'
    @Pointcut("execution(* com.lousing.poc.aop2.ProductService.add*(..))")
    public void addMethods() {}

    // 3️⃣ Match any method with an int parameter
    @Pointcut("args(int,..)")
    public void intArgumentMethods() {}

    // 4️⃣ Combine pointcuts
    @Pointcut("allProductMethods() && intArgumentMethods()")
    public void productMethodsWithIntArgs() {}

    // Advice using pointcut #1
    @Before("allProductMethods()")
    public void beforeAll(JoinPoint jp) {
        System.out.println("🔍 @Before (allProductMethods): " + jp.getSignature().getName());
    }

    // Advice using pointcut #2
    @Before("addMethods()")
    public void beforeAdd(JoinPoint jp) {
        System.out.println("➕ @Before (addMethods): " + jp.getSignature().getName());
    }

    // Advice using pointcut #4
    @Before("productMethodsWithIntArgs()")
    public void beforeIntMethods(JoinPoint jp) {
        System.out.println("🔢 @Before (intArgumentMethods): " + jp.getSignature().getName());
    }
}
```

### ✅ What this demonstrates

You now have pointcuts that match:

| Pointcut                      | Matches                         |
| ----------------------------- | ------------------------------- |
| `allProductMethods()`         | Any method in ProductService    |
| `addMethods()`                | Methods starting with `add`     |
| `intArgumentMethods()`        | Methods with an `int` parameter |
| `productMethodsWithIntArgs()` | Intersection of the above       |

This shows how pointcuts can be composed and reused.

---

## **Step 4: Trigger the service in your main class**

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ AOP Pointcut Demo Ready!");

        var service = context.getBean(com.lousing.poc.aop2.ProductService.class);

        System.out.println("\n--- addProduct() ---");
        service.addProduct("Laptop");

        System.out.println("\n--- deleteProduct() ---");
        service.deleteProduct(42);

        System.out.println("\n--- findProduct() ---");
        System.out.println(service.findProduct(7));

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
✅ AOP Pointcut Demo Ready!

--- addProduct() ---
🔍 @Before (allProductMethods): addProduct
➕ @Before (addMethods): addProduct
📦 Adding product: Laptop

--- deleteProduct() ---
🔍 @Before (allProductMethods): deleteProduct
🔢 @Before (intArgumentMethods): deleteProduct
🗑️ Deleting product with ID: 42

--- findProduct() ---
🔍 @Before (allProductMethods): findProduct
🔢 @Before (intArgumentMethods): findProduct
Product-7
----------------------------------
```

---

# ✅ Most Useful Pointcut Patterns (Cheat Sheet)

### ✅ Match all methods in a package

```
execution(* com.lousing.poc.services.*.*(..))
```

### ✅ Match all methods in a class

```
execution(* com.lousing.poc.services.OrderService.*(..))
```

### ✅ Match method by name prefix

```
execution(* *.save*(..))
```

### ✅ Match by return type

```
execution(String com.lousing..*(..))
```

### ✅ Match by argument types

```
args(String)
args(int,..)
```

### ✅ Match annotated methods

```
@annotation(org.springframework.transaction.annotation.Transactional)
```

---

# ✅ Summary

In this tutorial, you learned:

- What pointcut expressions are
- How to write pointcuts using `execution`, `args`, and name patterns
- How to reuse pointcuts with `@Pointcut` methods
- How to combine pointcuts using logical operators
- How pointcuts control where advice is applied

This sets you up for the final AOP tutorial:

✅ **1.6.4 Explain different types of Advice and when to use them**
