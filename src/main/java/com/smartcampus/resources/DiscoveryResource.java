package com.smartcampus.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Discovery endpoint providing essential API metadata.
 */
@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDiscoveryInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("version", "1.0.0");
        info.put("description", "Smart Campus Sensor & Room Management API");
        info.put("admin_contact", "admin@smartcampus.edu");
        
        // Mapping of primary resource collections (HATEOAS-style)
        Map<String, String> resources = new HashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");
        info.put("resources", resources);
        
        return info;
    }

    /**
     * Demo endpoint to trigger an intentional 500 error.
     * This proves the GenericExceptionMapper catches unexpected exceptions
     * and returns a safe JSON response — no raw stack trace exposed to the client.
     */
    @GET
    @Path("/error-test")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> triggerError() {
        // Simulating an unexpected server-side failure (e.g., a bug in production code)
        throw new RuntimeException("Simulated unexpected server error for demonstration purposes.");
    }
}
