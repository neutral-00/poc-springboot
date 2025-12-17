package com.lousing.poc.payments;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CreditCardProcessor implements PaymentProcessor{
    @Override
    public String process() {
        return "💳 Processing credit card payment";
    }
}
