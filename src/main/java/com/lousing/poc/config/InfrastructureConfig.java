package com.lousing.poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfrastructureConfig {

    @Bean
    public String appVersion() {
        return "1.0.0";
    }
}
