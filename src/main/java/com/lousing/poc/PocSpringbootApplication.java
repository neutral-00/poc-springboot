package com.lousing.poc;

import com.lousing.poc.config.NotificationMasterConfig;
import com.lousing.poc.service.NotificationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Import(NotificationMasterConfig.class)  // ✅ Single import loads ALL configs
public class PocSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Multiple Config Files Loaded!");
        System.out.println("----------------------------------");
    }
}