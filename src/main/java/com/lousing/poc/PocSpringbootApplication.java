package com.lousing.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {
        var context = SpringApplication.run(PocSpringbootApplication.class, args);

        System.out.println("\n✅ BeanFactoryPostProcessor & BeanPostProcessor Demo Ready!");

        var bean = context.getBean(com.lousing.poc.beans.SampleBean.class);
        bean.sayHello();

        System.out.println("----------------------------------");
	}
}
