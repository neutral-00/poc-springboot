package com.lousing.poc.util;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderIdGenerator {
    private final String orderId = "ORDER-" + UUID.randomUUID().toString();

    public String getOrderId() {
        return orderId;
    }
}
