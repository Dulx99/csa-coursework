package com.smartcampus.config;

import org.glassfish.jersey.server.ResourceConfig;
import jakarta.ws.rs.ApplicationPath;

/**
 * JAX-RS Configuration class.
 * Establishing the API's versioned entry point at /api/v1.
 */
@ApplicationPath("/api/v1")
public class AppConfig extends ResourceConfig {
    public AppConfig() {
        // Register the packages containing our resources, exceptions, and filters
        packages("com.smartcampus.resources", 
                 "com.smartcampus.exceptions", 
                 "com.smartcampus.filters");
    }
}
