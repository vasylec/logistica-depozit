package com.example.Model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double unitPrice;
    private Double volume;

    public Product() {}

    public Product(int id, String name, String description, double unitPrice, Double volume) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.volume = volume;
    }

    public Product(String name, String description, double unitPrice, Double volume) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.volume = volume;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
    public Double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }
}