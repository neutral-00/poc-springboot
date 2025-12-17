package com.lousing.poc.aop;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public String placeOrder(String item) {
        System.out.println("🛒 OrderService: Placing order for " + item);
        return "Order placed for " + item;
    }

    public void failOrder() {
        System.out.println("❌ OrderService: Simulating failure");
        throw new RuntimeException("Order failed due to system error");
    }
}
