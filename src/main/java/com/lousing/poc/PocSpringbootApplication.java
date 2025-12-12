package com.lousing.poc;

import com.lousing.poc.config.ScopeConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ScopeConfig.class)  // Load our Java config
public class PocSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Beans configured via Java Config!");
        System.out.println("----------------------------------");
    }
}