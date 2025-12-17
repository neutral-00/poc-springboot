package com.lousing.poc.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@Component
public class ProxyCreatingPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("proxyDemoService")) {
            System.out.println("🌀 Creating proxy for: " + beanName);
            return Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces().length > 0
                            ? bean.getClass().getInterfaces()
                            : new Class[]{bean.getClass()},
                    (proxy, method, args) -> {
                        System.out.println("🔍 Intercepted call: " + method.getName());
                        Object result = method.invoke(bean, args);
                        System.out.println("✅ Completed call: " + method.getName());
                        return result;
                    }
            );
        }
        return bean;
    }
}
