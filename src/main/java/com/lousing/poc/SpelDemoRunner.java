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