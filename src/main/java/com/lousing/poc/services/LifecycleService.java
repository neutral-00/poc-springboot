package com.lousing.poc.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class LifecycleService {
    public LifecycleService() {
        System.out.println("\n➡️ LifecycleService: Constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ LifecycleService: @PostConstruct initialization logic executed\n");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("🧹 LifecycleService: @PreDestroy cleanup logic executed");
    }
}
