package com.lousing.poc.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface PaypalService {
    String serviceProvider = "PayPal";
    String processPayment(Double amount);
    HashMap<String, String> getSenderInfo(String userId);
    void setSenderInfo(String userId, HashMap<String, String> info);
    HashMap<String, String> getReceiverInfo(String userId);
    void setReceiverInfo(String userId, HashMap<String, String> info);
}
