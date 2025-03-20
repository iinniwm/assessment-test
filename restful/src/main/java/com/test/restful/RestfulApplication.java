package com.test.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;

/**
 * Main application class
 * 
 * This application provides a RESTful API for user management with the following features:
 * - CRUD operations for users
 * - Validation
 * - Exception handling
 * - Pagination and sorting
 * - HATEOAS
 * - OpenAPI documentation
 * - Security
 * - Caching
 */
@SpringBootApplication
@EnableCaching
@EnableSpringDataWebSupport
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class RestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulApplication.class, args);
    }
}