package com.lousing.poc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({EmailConfig.class, SmsConfig.class, SlackConfig.class}) // ✅ Method 1: @Import
public class NotificationMasterConfig {
    // All 3 configs imported here
}
