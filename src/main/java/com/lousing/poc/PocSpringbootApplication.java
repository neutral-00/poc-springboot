package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ Stereotype Annotation Demo Ready!");

        var service = context.getBean(com.lousing.poc.services.MessageService.class);
        var util = context.getBean(com.lousing.poc.util.TimestampUtil.class);

        System.out.println(service.processMessage());
        System.out.println("Timestamp: " + util.now());

        System.out.println("----------------------------------");
	}
}
