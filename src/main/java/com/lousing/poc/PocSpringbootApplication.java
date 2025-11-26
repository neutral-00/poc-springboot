package com.lousing.poc;

import com.lousing.poc.util.OrderIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class PocSpringbootApplication {

	public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(PocSpringbootApplication.class, args);

        // accessing a prototype bean to demonstrate its functionality
        OrderIdGenerator orderIdGenerator1 = context.getBean(OrderIdGenerator.class);
        System.out.println("Generated Order ID: " + orderIdGenerator1.getOrderId());

        // accessing another instance to show that it's a different object
        OrderIdGenerator anotherOrderIdGenerator2 = context.getBean(OrderIdGenerator.class);
        System.out.println("Generated Another Order ID: " + anotherOrderIdGenerator2.getOrderId());
	}

}
