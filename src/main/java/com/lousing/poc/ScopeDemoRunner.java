package com.lousing.poc;

import com.lousing.poc.service.CounterNotificationService;
import com.lousing.poc.service.NotificationService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScopeDemoRunner implements CommandLineRunner {

    private final ApplicationContext context;
    private final NotificationService singletonBean;

    // ObjectFactory for prototype - gets NEW instance each call
    // ✅ FIXED: Use @Qualifier to resolve ambiguity
    @Autowired
    @Qualifier("prototypeNotificationService")
    private ObjectFactory<NotificationService> prototypeFactory;

    public ScopeDemoRunner(
            ApplicationContext context,
            @Qualifier("singletonNotificationService") NotificationService singletonBean
    ) {
        this.context = context;
        this.singletonBean = singletonBean;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === BEAN SCOPES DEMO ===");

        demoSingletonScope();
        demoPrototypeScope();
        demoObjectFactory();

        System.out.println("✅ Bean scopes demonstrated!");
        System.out.println("----------------------------------");
    }

    private void demoSingletonScope() {
        System.out.println("\n1️⃣ SINGLETON Scope (default):");
        System.out.println("   • 1 instance shared everywhere");

        NotificationService singleton1 = context.getBean("singletonNotificationService", NotificationService.class);
        NotificationService singleton2 = context.getBean("singletonNotificationService", NotificationService.class);

        System.out.println("singleton1.id = " + ((CounterNotificationService) singleton1).getInstanceId());
        System.out.println("singleton2.id = " + ((CounterNotificationService) singleton2).getInstanceId());
        System.out.println("✅ SAME instance (singleton)!");

        singleton1.send("Singleton test", "shared@company.com");
    }

    private void demoPrototypeScope() {
        System.out.println("\n2️⃣ PROTOTYPE Scope:");
        System.out.println("   • NEW instance every time");

        NotificationService proto1 = context.getBean("prototypeNotificationService", NotificationService.class);
        NotificationService proto2 = context.getBean("prototypeNotificationService", NotificationService.class);

        System.out.println("proto1.id = " + ((CounterNotificationService) proto1).getInstanceId());
        System.out.println("proto2.id = " + ((CounterNotificationService) proto2).getInstanceId());
        System.out.println("✅ DIFFERENT instances (prototype)!");
    }

    private void demoObjectFactory() {
        System.out.println("\n3️⃣ ObjectFactory<Prototype> (for injection):");

        NotificationService factory1 = prototypeFactory.getObject();
        NotificationService factory2 = prototypeFactory.getObject();

        System.out.println("factory1.id = " + ((CounterNotificationService) factory1).getInstanceId());
        System.out.println("factory2.id = " + ((CounterNotificationService) factory2).getInstanceId());
        System.out.println("✅ NEW instances each factory.getObject()!");
    }
}
