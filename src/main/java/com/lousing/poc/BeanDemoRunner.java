package com.lousing.poc;

import com.lousing.poc.service.EmailNotificationService;
import com.lousing.poc.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component  // Spring will create & wire this bean automatically
public class BeanDemoRunner implements CommandLineRunner {
    private final ApplicationContext context;
    private final NotificationService emailService;
    private final NotificationService smsService;

    // 1️⃣ Constructor Injection (PREFERRED)
    public BeanDemoRunner(
            ApplicationContext context,
            @Qualifier("emailNotificationService") NotificationService emailService,
            @Qualifier("smsNotificationService") NotificationService smsService
    ) {
        this.context = context;
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === ACCESSING BEANS ===");

        // METHOD 1: ApplicationContext.getBean() - Programmatic
        demoGetBean();

        // METHOD 2: Constructor Injection (already wired above)
        demoConstructorInjection();

        // METHOD 3: Field Injection (alternative - less preferred)
        demoFieldInjection();

        System.out.println("✅ All bean access methods working!");
        System.out.println("----------------------------------");
    }

    private void demoGetBean() {
        System.out.println("\n1️⃣ ApplicationContext.getBean():");

        // By TYPE (ambiguous - multiple NotificationService beans)
        // NotificationService any = context.getBean(NotificationService.class);

        // By NAME (method name from 1.2.1)
        NotificationService emailByName = context.getBean("emailNotificationService", NotificationService.class);
        emailByName.send("Manual lookup test", "dev@company.com");

        // By CLASS (exact implementation)
        EmailNotificationService emailImpl = context.getBean(EmailNotificationService.class);
        emailImpl.send("By class lookup", "admin@company.com");
    }

    private void demoConstructorInjection() {
        System.out.println("\n2️⃣ Constructor Injection:");
        emailService.send("Constructor injected", "user1@example.com");
        smsService.send("Constructor injected", "+1234567890");
    }

    private void demoFieldInjection() {
        System.out.println("\n3️⃣ Field Injection:");
        testField.send("Field injected", "test@example.com");
    }

    // 3️⃣ Field Injection example (@Autowired on field)
    @Autowired
    @Qualifier("smsNotificationService")
    private NotificationService testField;
}
