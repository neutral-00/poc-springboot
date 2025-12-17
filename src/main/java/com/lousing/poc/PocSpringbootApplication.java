package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ Bean Creation Order Demo Ready!");
        System.out.println("----------------------------------");
	}
}
