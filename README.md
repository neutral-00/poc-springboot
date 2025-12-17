# 1.5.3 Explain how Spring proxies add behavior at runtime

### Project Metadata
- Repository: https://github.com/neutral-00/poc-springboot
- **Parent Branch:** `main`
- **Branch:** `1.5.3-explain-how-spring-proxies-add-behavior-at-runtime`

---

## 🎯 Learning Objectives
- [ ] Understand what a proxy is in Spring
- [ ] Learn why Spring uses proxies (AOP, transactions, caching, security)
- [ ] Observe proxy creation at runtime
- [ ] Understand JDK dynamic proxies vs CGLIB proxies
- [ ] See how method interception works

---

## **Scenario**
Your team wants to understand how Spring magically adds behavior like:

- Transaction boundaries
- Security checks
- Logging
- Caching
- AOP advice

…without modifying your actual code.

The secret: **Spring creates proxies around your beans at runtime.**

In this tutorial, you will:

- Create a simple service
- Add a proxy around it using a `BeanPostProcessor`
- Observe how method calls are intercepted
- Understand how Spring AOP works under the hood

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.5.3-explain-how-spring-proxies-add-behavior-at-runtime
```

---

## **Step 2: Create a service that will be proxied**

Create:

```
com.lousing.poc.services.ProxyDemoService
```

```java
package com.lousing.poc.services;

import org.springframework.stereotype.Service;

@Service
public class ProxyDemoService {

    public void doWork() {
        System.out.println("🛠️ ProxyDemoService: Doing important work...");
    }
}
```

---

## **Step 3: Create a BeanPostProcessor that wraps the bean in a proxy**

This simulates how Spring AOP works internally.

Create:

```
com.lousing.poc.processors.ProxyCreatingPostProcessor
```

```java
package com.lousing.poc.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@Component
public class ProxyCreatingPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (beanName.equals("proxyDemoService")) {
            System.out.println("🌀 Creating proxy for: " + beanName);

            return Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces().length > 0
                            ? bean.getClass().getInterfaces()
                            : new Class[]{bean.getClass()},
                    (proxy, method, args) -> {
                        System.out.println("🔍 Intercepted call: " + method.getName());
                        Object result = method.invoke(bean, args);
                        System.out.println("✅ Completed call: " + method.getName());
                        return result;
                    }
            );
        }

        return bean;
    }
}
```

### ✅ What this demonstrates

- Spring wraps beans with proxies **after initialization**
- Method calls go through the proxy first
- The proxy can add behavior before/after the real method
- This is exactly how Spring AOP, transactions, and security work

---

## **Step 4: Update the service to implement an interface**

JDK dynamic proxies require an interface.

Create:

```
com.lousing.poc.services.WorkService
```

```java
package com.lousing.poc.services;

public interface WorkService {
    void doWork();
}
```

Update `ProxyDemoService`:

```java
@Service
public class ProxyDemoService implements WorkService {

    @Override
    public void doWork() {
        System.out.println("🛠️ ProxyDemoService: Doing important work...");
    }
}
```

---

## **Step 5: Trigger the proxy in your main class**

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ Proxy Demo Ready!");

        var service = context.getBean(com.lousing.poc.services.WorkService.class);
        service.doWork();

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
🌀 Creating proxy for: proxyDemoService

✅ Proxy Demo Ready!
🔍 Intercepted call: doWork
🛠️ ProxyDemoService: Doing important work...
✅ Completed call: doWork
----------------------------------
```

---

# ✅ Understanding Spring Proxies

### ✅ Why Spring uses proxies
- AOP (logging, metrics, security)
- Transactions (`@Transactional`)
- Caching (`@Cacheable`)
- Async execution (`@Async`)
- Event listeners

### ✅ Two types of proxies

| Proxy Type | When Used | Notes |
|------------|-----------|-------|
| **JDK Dynamic Proxy** | Bean implements an interface | Default, lightweight |
| **CGLIB Proxy** | No interface present | Subclass-based, more flexible |

Spring Boot defaults to **CGLIB** unless configured otherwise.

---

# ✅ Summary

In this tutorial, you learned:

- What a proxy is and why Spring uses them
- How Spring adds behavior at runtime without modifying your code
- How `BeanPostProcessor` is used to wrap beans in proxies
- The difference between JDK proxies and CGLIB proxies
- How method interception works internally

This sets the stage for the next tutorial:

✅ **1.5.4 Describe how Spring determines bean creation order**
