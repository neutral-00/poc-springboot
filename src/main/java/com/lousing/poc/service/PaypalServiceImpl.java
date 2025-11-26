package com.lousing.poc.service;

import java.util.HashMap;

public class PaypalServiceImpl implements PaypalService{
    @Override
    public String processPayment(Double amount) {
        return "";
    }

    @Override
    public HashMap<String, String> getSenderInfo(String userId) {
        return null;
    }

    @Override
    public void setSenderInfo(String userId, HashMap<String, String> info) {

    }

    @Override
    public HashMap<String, String> getReceiverInfo(String userId) {
        return null;
    }

    @Override
    public void setReceiverInfo(String userId, HashMap<String, String> info) {

    }
}
