package com.example.invoice.model;

public class Dealer {
    private String id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String taxId;

    public Dealer() {}

    public Dealer(String id, String name, String address, String email, String phone, String taxId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.taxId = taxId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getTaxId() { return taxId; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setTaxId(String taxId) { this.taxId = taxId; }
}
