package com.lousing.poc.payments;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentProcessor paymentProcessor;

    // Constructor Injection with Qualifier to specify which implementation to use
    // Here we can change "creditCardProcessor" to "paypalProcessor" to switch implementations
    // For to change to PaypalProcessor, use @Qualifier("paypalProcessor") as
    // public PaymentService(@Qualifier("paypalProcessor") PaymentProcessor paymentProcessor) {
    public PaymentService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public String makePayment() {
        return paymentProcessor.process();
    }
}
