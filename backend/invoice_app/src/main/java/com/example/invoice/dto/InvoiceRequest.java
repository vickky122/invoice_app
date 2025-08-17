package com.example.invoice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class InvoiceRequest {

    @NotBlank(message = "dealerId is required")
    private String dealerId;

    @NotBlank(message = "vehicleId is required")
    private String vehicleId;

    @NotBlank(message = "customerName is required")
    @Size(max = 100, message = "customerName max length is 100")
    private String customerName;

    public InvoiceRequest() {}

    public InvoiceRequest(String dealerId, String vehicleId, String customerName) {
        this.dealerId = dealerId;
        this.vehicleId = vehicleId;
        this.customerName = customerName;
    }

    public String getDealerId() { return dealerId; }
    public void setDealerId(String dealerId) { this.dealerId = dealerId; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}
