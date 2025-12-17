# 1.3.3 Use the Spring Expression Language (SpEL)

### Project Metadata
- Repository: [https://github.com/neutral-00/poc-springboot](https://github.com/neutral-00/poc-springboot)
- **Parent Branch:** `main` (bare-bones Spring Boot app)
- **Branch:** `1.3.3-use-spring-expression-language-spel`

## 🎯 Learning Objectives
- [ ] Use Spring Expression Language (SpEL) (`#{...}` dynamic expressions)

**Scenario:** Pure SpEL demo - **no services, no interfaces**. Just `@Value("#{expressions}")` showing dynamic runtime evaluation.

## Step 1: Simple SpEL Properties

```properties
# src/main/resources/application.properties (NEW)
spring.profiles.active=dev
```

## Step 2: Pure SpEL Demo

```java
// com.lousing.poc.SpelDemoRunner.java (NEW)
package com.lousing.poc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SpelDemoRunner implements CommandLineRunner {

    // 🔥 PURE SpEL - No services, no complexity!

    // ⚠️ WARNING: below will read JVM property, so start your app with `java -Dspring.profiles.active=dev -jar app.jar`
    // when run with intellij or mvn spring-boot:run it will default to 'default'
    // it will ignore application.properties settings
    @Value("#{T(System).getProperty('spring.profiles.active', 'default')}")
    private String activeProfile;

    @Value("#{T(java.lang.System).getenv('USER') ?: T(java.lang.System).getProperty('user.name')}")
    private String currentUser;

    @Value("#{T(java.lang.Math).random() * 1000}")
    private double randomValue;

    @Value("#{T(java.time.LocalDateTime).now().getHour() >= 9 && T(java.time.LocalDateTime).now().getHour() <= 17 ? 'BUSINESS_HOURS' : 'OFF_HOURS'}")
    private String businessHours;

    @Value("#{T(java.lang.Runtime).getRuntime().availableProcessors()}")
    private int cpuCount;

    @Value("#{T(java.lang.Math).PI}")
    private double piValue;

    @Value("#{T(java.time.LocalDate).now().getDayOfMonth()}")
    private int dayOfMonth;

    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === PURE SpEL DEMO ===\n");

        System.out.println("👤 Active Profile: " + activeProfile);
        System.out.println("🙍 Current User:   " + currentUser);
        System.out.println("🎲 Random Value:  " + String.format("%.0f", randomValue));
        System.out.println("⏰ Business Hours: " + businessHours);
        System.out.println("💻 CPU Cores:     " + cpuCount);
        System.out.println("📊 π Value:       " + String.format("%.4f", piValue));
        System.out.println("📅 Day of Month:  " + dayOfMonth);

        System.out.println("\n✅ SpEL mastery complete!");
        System.out.println("----------------------------------");
    }
}
```

## Step 3: Updated Main Application

```java
// com.lousing.poc.PocSpringbootApplication.java (UPDATED)
package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class PocSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Pure SpEL Ready!");
        System.out.println("----------------------------------");
    }
}
```

## Expected Output

```
✅ Pure SpEL Ready!
----------------------------------

🚀 === PURE SpEL DEMO ===

👤 Active Profile: default
🎲 Random Value:  742
⏰ Business Hours: BUSINESS_HOURS
💻 CPU Cores:     8
📊 π Value:       3.1416
📅 Day of Month:  14

✅ SpEL mastery complete!
----------------------------------
```

## SpEL Syntax Cheat Sheet

| Type | Expression | Example Output |
|------|------------|----------------|
| **Property** | `#{T(System).getProperty('name')}` | `default` |
| **Random** | `#{T(Math).random() * 1000}` | `742.123` |
| **Static** | `#{T(Math).PI}` | `3.14159` |
| **Time** | `#{T(LocalDateTime).now()}` | Current time |
| **Condition** | `#{condition ? 'YES' : 'NO'}` | Dynamic |
| **Math** | `#{1 + 2 * 3}` | `7` |

## File Structure (3 Files Total!)

```
1.3.3-use-spring-expression-language-spel/
├── src/main/resources/
│   └── application.properties      # NEW
├── src/main/java/com/lousing/poc/
│   ├── PocSpringbootApplication.java  # UPDATED
│   └── SpelDemoRunner.java            # NEW
```

## Verification Checklist

**✅ Complete when:**
- [ ] **Random value changes** each run
- [ ] **Business hours** switches (9AM-5PM)
- [ ] **π shows 3.1415...**
- [ ] **CPU cores** matches your machine
- [ ] **Zero service files** - pure SpEL focus

**🎉 1.3 COMPLETE!** Properties + Profiles + Pure SpEL mastery!