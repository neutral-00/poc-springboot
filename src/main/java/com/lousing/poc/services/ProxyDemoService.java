package com.lousing.poc.services;

import org.springframework.stereotype.Service;

@Service
public class ProxyDemoService implements WorkService {
    public void doWork() {
        System.out.println("🛠️ ProxyDemoService: Doing important work...");
    }
}
