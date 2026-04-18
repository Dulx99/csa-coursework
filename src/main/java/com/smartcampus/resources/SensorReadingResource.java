package com.smartcampus.resources;

import com.smartcampus.exceptions.SensorUnavailableException;
import com.smartcampus.models.Sensor;
import com.smartcampus.models.SensorReading;
import com.smartcampus.services.DataService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Sub-resource handling readings for a specific sensor.
 * Not annotated with @Path, as it's returned by the SensorResource locator method.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;
    private final DataService dataService = DataService.getInstance();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * GET /: Fetch historical readings for the given sensor.
     */
    @GET
    public List<SensorReading> getReadings() {
        // The sub-resource is accessed dynamically based on the sensorId we got from the parent class
        // If the sensor doesn't exist we could throw a 404, but we'll just return the history (empty list)
        return dataService.getReadings(sensorId);
    }

    /**
     * POST /: Append a new reading. Updates the parent sensor's currentValue side effect.
     */
    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = dataService.getSensors().get(sensorId);
        
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found.")
                    .build();
        }

        // State Constraint (403 Forbidden) check
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is currently in MAINTENANCE mode and cannot accept new readings.");
        }

        // If the client only sent the raw double value without an ID, let's create a healthy object
        if (reading.getId() == null || reading.getId().isEmpty()) {
            reading = new SensorReading(reading.getValue());
        }

        // Saving the reading and handling the parent's currentValue side-effect dynamically via addReading()
        dataService.addReading(sensorId, reading);

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
