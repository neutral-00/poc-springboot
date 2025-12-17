package com.lousing.poc;

import com.lousing.poc.config.AppConfig;
import com.lousing.poc.services.GreetingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class PocSpringbootApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("\n✅ Annotation Config Demo Ready!");

        GreetingService service = context.getBean(GreetingService.class);
        System.out.println(service.greet("Annotation-Based Config"));
        System.out.println("----------------------------------");
    }
}
