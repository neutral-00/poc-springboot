package com.lousing.poc.aop3;

import org.springframework.stereotype.Service;

@Service
public class TaskService {
    public void startTask() {
        System.out.println("🚀 Task started");
    }

    public String completeTask() {
        System.out.println("✅ Task completed");
        return "Task Result";
    }

    public void failTask() {
        System.out.println("💥 Task failed");
        throw new RuntimeException("Simulated task failure");
    }
}
