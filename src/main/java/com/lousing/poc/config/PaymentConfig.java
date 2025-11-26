package com.lousing.poc.config;

import com.lousing.poc.service.GpayService;
import com.lousing.poc.service.GpayServiceImpl;
import com.lousing.poc.service.PaypalService;
import com.lousing.poc.service.PaypalServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class PaymentConfig {
    // Payment related beans and configurations to be enabled based on if the feature is enabled

    @Bean
    @ConditionalOnProperty
            (name = "app.payment.gpay.enabled", havingValue = "true")
    public GpayService gpayService() {
        System.out.println("GPay Service Bean Created");
        return new GpayServiceImpl();
    }

    @Bean
    @ConditionalOnProperty
            (name = "app.payment.paypal.enabled", havingValue = "true")
    public PaypalService paypalService() {
        System.out.println("PayPal Service Bean Created");
        return new PaypalServiceImpl();
    }

}
