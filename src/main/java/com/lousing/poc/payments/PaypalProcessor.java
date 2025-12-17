package com.lousing.poc.payments;

import org.springframework.stereotype.Component;

@Component
public class PaypalProcessor implements PaymentProcessor{
    @Override
    public String process() {
        return "🅿️ Processing PayPal payment";
    }
}
