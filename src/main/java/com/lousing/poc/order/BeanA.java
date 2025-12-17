package com.lousing.poc.order;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
public class BeanA {

    public BeanA() {
        System.out.println("✅ BeanA created");
    }
}