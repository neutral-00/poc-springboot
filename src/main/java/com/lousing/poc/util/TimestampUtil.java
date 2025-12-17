package com.lousing.poc.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimestampUtil {
    public String now() {
        return LocalDateTime.now().toString();
    }
}
