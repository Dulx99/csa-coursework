package com.smartcampus.services;

import com.smartcampus.models.Room;
import com.smartcampus.models.Sensor;
import com.smartcampus.models.SensorReading;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Singleton service for managing in-memory storage.
 * As per regulations, no external database is used.
 */
public class DataService {
    private static DataService instance;

    // In-memory collections
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    private final Map<String, List<SensorReading>> readingsHistory = new ConcurrentHashMap<>();

    private DataService() {
        // Initialize with some sample data if needed, or leave empty for CRUD tasks.
    }

    public static synchronized DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    // Room operations
    public Map<String, Room> getRooms() { return rooms; }
    
    // Sensor operations
    public Map<String, Sensor> getSensors() { return sensors; }

    // Reading operations
    public List<SensorReading> getReadings(String sensorId) {
        return readingsHistory.computeIfAbsent(sensorId, k -> new CopyOnWriteArrayList<>());
    }

    public void addReading(String sensorId, SensorReading reading) {
        List<SensorReading> history = getReadings(sensorId);
        history.add(reading);
        
        // Side effect: Update the sensor's current value
        Sensor sensor = sensors.get(sensorId);
        if (sensor != null) {
            sensor.setCurrentValue(reading.getValue());
        }
    }
}
