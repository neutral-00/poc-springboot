package com.lousing.poc;

import com.lousing.poc.service.NotificationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MultiConfigDemoRunner implements CommandLineRunner {

    private final ApplicationContext context;

    public MultiConfigDemoRunner(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n🚀 === MULTIPLE CONFIG FILES ===");

        // Demo all 3 beans from 3 different config files
        demoAllNotificationServices();
        demoBeanCount();

        System.out.println("✅ Multiple configs working!");
        System.out.println("----------------------------------");
    }

    private void demoAllNotificationServices() {
        System.out.println("\n📋 Beans from 3 config files:");

        NotificationService email = context.getBean("emailNotificationService", NotificationService.class);
        email.send("Email config test", "dev@company.com");

        NotificationService sms = context.getBean("smsNotificationService", NotificationService.class);
        sms.send("SMS config test", "+1234567890");

        NotificationService slack = context.getBean("slackNotificationService", NotificationService.class);
        slack.send("Slack config test", "dev-channel");
    }

    private void demoBeanCount() {
        String[] beanNames = context.getBeanDefinitionNames();
        long notificationBeans = java.util.Arrays.stream(beanNames)
                .filter(name -> name.contains("NotificationService"))
                .count();

        System.out.println("\n📊 Total NotificationService beans: " + notificationBeans);
    }
}
