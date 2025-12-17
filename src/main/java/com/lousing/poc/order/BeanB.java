package com.lousing.poc.order;

import org.springframework.stereotype.Component;

@Component
public class BeanB {

    private final BeanA beanA;

    public BeanB(BeanA beanA) {
        this.beanA = beanA;
        System.out.println("✅ BeanB created (depends on BeanA)");
    }
}