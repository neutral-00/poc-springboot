package com.lousing.poc.beans;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SampleBean {
    public SampleBean() {
        System.out.println("➡️ SampleBean: Constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("➡️ SampleBean: init method called");
    }

    public void sayHello() {
        System.out.println("👋 Hello from SampleBean");
    }
}
