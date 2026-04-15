package com.smartcampus.models;

import java.util.UUID;

/**
 * Represents a discrete data point captured by a Sensor.
 */
public class SensorReading {
    private String id;          // Unique reading event ID (UUID recommended)
    private long timestamp;     // Epoch time (ms) when the reading was captured
    private double value;       // The actual metric recorded by the hardware

    // Default constructor for JSON-B
    public SensorReading() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
    }

    public SensorReading(double value) {
        this();
        this.value = value;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}
