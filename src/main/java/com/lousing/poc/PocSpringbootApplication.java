package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ AOP Advice Demo Ready!");

        var service = context.getBean(com.lousing.poc.aop.OrderService.class);

        System.out.println("\n--- Successful Order ---");
        service.placeOrder("Laptop");

        System.out.println("\n--- Failed Order ---");
        try {
            service.failOrder();
        } catch (Exception ignored) {}

        System.out.println("----------------------------------");
	}
}
