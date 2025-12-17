package com.lousing.poc;

import com.lousing.poc.services.LifecycleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ Lifecycle Demo Ready!");

        // Force bean retrieval so logs appear immediately
        context.getBean(LifecycleService.class);

        System.out.println("----------------------------------");
	}
}
