package com.lousing.poc.config;

import com.lousing.poc.service.NotificationRouter;
import com.lousing.poc.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import({EmailConfig.class, SmsConfig.class, SlackConfig.class}) // ✅ Method 1: @Import
public class NotificationMasterConfig {
    // ✅ WAY 1: Method parameter injection (Spring auto-wires ALL NotificationService beans)
    @Bean
    public NotificationRouter notificationRouter(List<NotificationService> allNotificationServices) {
        return new NotificationRouter(allNotificationServices);
    }

    // ✅ WAY 2: Explicit dependency injection (shows order control)
    @Bean
    @DependsOn({"emailNotificationService", "smsNotificationService", "slackNotificationService"})
    public NotificationRouter orderedNotificationRouter(
            NotificationService emailNotificationService,
            NotificationService smsNotificationService,
            NotificationService slackNotificationService) {
        return new NotificationRouter(List.of(
                emailNotificationService, smsNotificationService, slackNotificationService
        ));
    }
}
