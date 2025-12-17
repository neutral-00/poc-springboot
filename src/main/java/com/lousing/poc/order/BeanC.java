package com.lousing.poc.order;

import org.springframework.stereotype.Component;

@Component
public class BeanC {

    private final BeanB beanB;

    public BeanC(BeanB beanB) {
        this.beanB = beanB;
        System.out.println("✅ BeanC created (depends on BeanB)");
    }
}