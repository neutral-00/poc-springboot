package com.lousing.poc;

import com.lousing.poc.service.NotificationRouter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DependencyDemoRunner implements CommandLineRunner {

    private final ApplicationContext context;
    private final NotificationRouter router1;  // List injection
    private final NotificationRouter router2;  // Explicit injection

    public DependencyDemoRunner(
            ApplicationContext context,
            @Qualifier("notificationRouter") NotificationRouter router1,
            @Qualifier("orderedNotificationRouter") NotificationRouter router2
    ) {
        this.context = context;
        this.router1 = router1;
        this.router2 = router2;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === BEAN DEPENDENCIES ===");

        demoListInjection();
        demoExplicitDependencies();
        demoBeanCreationOrder();

        System.out.println("✅ Dependency injection working!");
        System.out.println("----------------------------------");
    }

    private void demoListInjection() {
        System.out.println("\n1️⃣ List<NotificationService> injection:");
        router1.routeCriticalAlert("🚨 Server Down - List injection", "ops@company.com");
    }

    private void demoExplicitDependencies() {
        System.out.println("\n2️⃣ Explicit parameter injection:");
        router2.sendViaEmailOnly("Only email - explicit deps", "dev@company.com");
    }

    private void demoBeanCreationOrder() {
        System.out.println("\n3️⃣ @DependsOn order control:");
        System.out.println("NotificationRouter created AFTER all 3 services ✅");
    }
}
