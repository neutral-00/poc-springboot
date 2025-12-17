package com.lousing.poc.processors;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (beanName.equals("sampleBean")) {
            System.out.println("🔍 BeanPostProcessor BEFORE init: " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (beanName.equals("sampleBean")) {
            System.out.println("✅ BeanPostProcessor AFTER init: " + beanName + "\n");
        }
        return bean;
    }
}
