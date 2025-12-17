package com.lousing.poc.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("\n🏗️ BeanFactoryPostProcessor: Modifying bean definitions...");

        BeanDefinition def = beanFactory.getBeanDefinition("sampleBean");
        def.setDescription("Modified by BeanFactoryPostProcessor");
    }
}
