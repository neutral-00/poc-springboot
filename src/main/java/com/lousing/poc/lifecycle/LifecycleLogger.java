package com.lousing.poc.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class LifecycleLogger implements InitializingBean, DisposableBean {

    public LifecycleLogger() {
        System.out.println("➡️ Constructor: Bean instance created");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("✅ @PostConstruct: Dependencies injected, bean initialized");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("🔧 InitializingBean.afterPropertiesSet(): Additional initialization logic");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("🧹 @PreDestroy: Cleanup before bean destruction");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("🗑️ DisposableBean.destroy(): Final cleanup logic");
    }
}
