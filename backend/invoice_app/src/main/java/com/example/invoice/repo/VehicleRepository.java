package com.example.invoice.repo;

import com.example.invoice.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class VehicleRepository {

    private final Map<String, Vehicle> vehicles = new HashMap<>();

    public VehicleRepository() {
        // Seed sample vehicles
        vehicles.put("V101", new Vehicle(
                "V101", "Toyota", "Camry", 2023, "JT1234567890VIN01", new BigDecimal("25000.00")
        ));
        vehicles.put("V102", new Vehicle(
                "V102", "Honda", "Civic", 2024, "HG9876543210VIN02", new BigDecimal("22000.00")
        ));
        vehicles.put("V103", new Vehicle(
                "V103", "Tesla", "Model 3", 2024, "5YJ3E1EA7JFVIN03", new BigDecimal("38000.00")
        ));
    }

    public Optional<Vehicle> findById(String id) {
        return Optional.ofNullable(vehicles.get(id));
    }
}
