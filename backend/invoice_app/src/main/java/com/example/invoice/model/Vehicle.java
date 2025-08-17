package com.example.invoice.model;

import java.math.BigDecimal;

public class Vehicle {
    private String id;
    private String make;
    private String model;
    private int year;
    private String vin;
    private BigDecimal basePrice;

    public Vehicle() {}

    public Vehicle(String id, String make, String model, int year, String vin, BigDecimal basePrice) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.basePrice = basePrice;
    }

    public String getId() { return id; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getVin() { return vin; }
    public BigDecimal getBasePrice() { return basePrice; }

    public void setId(String id) { this.id = id; }
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }
    public void setVin(String vin) { this.vin = vin; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
}
