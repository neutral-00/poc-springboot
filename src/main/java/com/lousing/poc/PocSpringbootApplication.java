package com.lousing.poc;

import com.lousing.poc.config.ProfileConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ProfileConfig.class)
public class PocSpringbootApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);
        System.out.println("✅ Profiles Ready!");
        System.out.println("----------------------------------");
	}
}
