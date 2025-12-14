package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Pure SpEL Demo Ready!");
        System.out.println("----------------------------------");
	}
}
