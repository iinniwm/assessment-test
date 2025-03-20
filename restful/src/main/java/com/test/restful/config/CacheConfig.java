package com.test.restful.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cache configuration for the application
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configure cache manager
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users");
    }
}