package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);
        String appVersion = context.getBean("appVersion", String.class);
        System.out.println("\nApplication Version: " + appVersion);
        System.out.println("--------------------------\n");
	}
}
