# 1.5.2 Use a BeanFactoryPostProcessor and a BeanPostProcessor

### Project Metadata

- Repository: https://github.com/neutral-00/poc-springboot
- **Parent Branch:** `main`
- **Branch:** `1.5.2-use-beanfactorypostprocessor-and-beanpostprocessor`

---

## 🎯 Learning Objectives

- [ ] Understand what a `BeanFactoryPostProcessor` does
- [ ] Understand what a `BeanPostProcessor` does
- [ ] Observe how they modify bean definitions and bean instances
- [ ] Implement both processors in a Spring Boot application
- [ ] See the order in which they run during the lifecycle

---

## **Scenario**

Your team wants to understand how Spring allows deep customization of the container itself.  
Two powerful extension points exist:

### ✅ **BeanFactoryPostProcessor**

- Runs **before** any beans are created
- Allows modifying **bean definitions** (metadata)

### ✅ **BeanPostProcessor**

- Runs **after** bean instantiation
- Allows modifying **bean instances**

You will implement both and observe their behavior in the logs.

---

# ✅ Step-by-Step Tutorial

---

## **Step 1: Create a new branch**

```bash
git checkout main
git pull
git checkout -b 1.5.2-use-beanfactorypostprocessor-and-beanpostprocessor
```

---

## **Step 2: Create a simple bean to observe processing**

Create:

```
com.lousing.poc.beans.SampleBean
```

```java
package com.lousing.poc.beans;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SampleBean {
    public SampleBean() {
        System.out.println("➡️ SampleBean: Constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("➡️ SampleBean: init method called");
    }

    public void sayHello() {
        System.out.println("👋 Hello from SampleBean");
    }
}
```

---

## **Step 3: Create a BeanFactoryPostProcessor**

Create:

```
com.lousing.poc.processors.CustomBeanFactoryPostProcessor
```

```java
package com.lousing.poc.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {

        System.out.println("🏗️ BeanFactoryPostProcessor: Modifying bean definitions...");

        BeanDefinition def = beanFactory.getBeanDefinition("sampleBean");
        def.setDescription("Modified by BeanFactoryPostProcessor");
    }
}
```

### ✅ What this does

- Runs **before** any beans are created
- Accesses and modifies the **bean definition**
- Does _not_ touch the actual bean instance

---

## **Step 4: Create a BeanPostProcessor**

Create:

```
com.lousing.poc.processors.CustomBeanPostProcessor
```

```java
package com.lousing.poc.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if (beanName.equals("sampleBean")) {
            System.out.println("🔍 BeanPostProcessor BEFORE init: " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (beanName.equals("sampleBean")) {
            System.out.println("✅ BeanPostProcessor AFTER init: " + beanName);
        }
        return bean;
    }
}
```

### ✅ What this does

- Runs **after** bean instantiation
- Runs **before and after** initialization
- Can wrap, replace, or enhance beans (basis for AOP proxies)

---

## **Step 5: Trigger bean creation in your main class**

Modify your main class:

```java
@SpringBootApplication
public class PocSpringbootApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ BeanFactoryPostProcessor & BeanPostProcessor Demo Ready!");

        var bean = context.getBean(com.lousing.poc.beans.SampleBean.class);
        bean.sayHello();

        System.out.println("----------------------------------");
    }
}
```

---

## **Step 6: Run the application**

```bash
mvn spring-boot:run
```

Expected output (order matters):

```
🏗️ BeanFactoryPostProcessor: Modifying bean definitions...
➡️ SampleBean: Constructor called
🔍 BeanPostProcessor BEFORE init: sampleBean
✅ BeanPostProcessor AFTER init: sampleBean

✅ BeanFactoryPostProcessor & BeanPostProcessor Demo Ready!
👋 Hello from SampleBean
----------------------------------
```

---

# ✅ Understanding the Difference

| Feature     | BeanFactoryPostProcessor                      | BeanPostProcessor                         |
| ----------- | --------------------------------------------- | ----------------------------------------- |
| Runs when   | Before bean creation                          | After bean creation                       |
| Operates on | Bean **definitions**                          | Bean **instances**                        |
| Use cases   | Modify metadata, change scope, set properties | Wrap beans, add proxies, enhance behavior |
| Common in   | Framework-level code                          | AOP, logging, auditing                    |

---

# ✅ Summary

In this tutorial, you learned:

- How to implement a `BeanFactoryPostProcessor`
- How to implement a `BeanPostProcessor`
- How they fit into the Spring Bean lifecycle
- How they differ in purpose and timing
- How to observe their behavior in a running Spring Boot app

This sets you up perfectly for the next tutorial:

✅ **1.5.3 Explain how Spring proxies add behavior at runtime**
