package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ AOP Pointcut Demo Ready!");

        var service = context.getBean(com.lousing.poc.aop2.ProductService.class);

        System.out.println("\n--- addProduct() ---");
        service.addProduct("Laptop");

        System.out.println("\n--- deleteProduct() ---");
        service.deleteProduct(42);

        System.out.println("\n--- findProduct() ---");
        System.out.println(service.findProduct(7));

        System.out.println("----------------------------------");
	}
}
