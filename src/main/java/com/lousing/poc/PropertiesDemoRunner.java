package com.lousing.poc;

import com.lousing.poc.service.PropertiesDemoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PropertiesDemoRunner implements CommandLineRunner {

    private final PropertiesDemoService demoService;

    public PropertiesDemoRunner(PropertiesDemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public void run(String... args) throws Exception {
        demoService.demoConfiguration();
        System.out.println("\n✅ External properties loaded successfully!");
        System.out.println("----------------------------------");
    }
}
