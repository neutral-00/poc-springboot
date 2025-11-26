package com.lousing.poc.config;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class CacheConfig {

    @Bean
    @Profile("dev")
    public Cache devCache(){
        // Configure and return the development DataSource
        System.out.println("Development cache configured");
        return new ConcurrentMapCache("lousingCache");
    }

    // cache config for prod
    @Bean
    @Profile("prod")
    public Cache prodCache() {
        // Configure and return the production DataSource
        System.out.println("Production Cache configured");
        return null; // Replace with actual production cache implementation like RedisCache
    }
}
