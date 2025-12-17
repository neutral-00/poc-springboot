package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ AOP Advice Types Demo Ready!");

        var service = context.getBean(com.lousing.poc.aop3.TaskService.class);

        System.out.println("\n--- startTask() ---");
        service.startTask();

        System.out.println("\n--- completeTask() ---");
        System.out.println(service.completeTask());

        System.out.println("\n--- failTask() ---");
        try {
            service.failTask();
        } catch (Exception ignored) {}

        System.out.println("----------------------------------");
	}
}
