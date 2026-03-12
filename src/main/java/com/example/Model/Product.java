package com.example.Model;

public class Product implements Cloneable {
    private int id;
    private String name;
    private String description;
    private double unitPrice;
    private Double volume;
    private int quantity;

    public Product(int id, String name, String description, double unitPrice, Double volume, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.volume = volume;
        this.quantity = quantity;
    }

    @Override
    public Product clone() {
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return name + " -> quantity: " + quantity;
    }

    public String printProduct() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", unitPrice=" + unitPrice
                + ", volume=" + volume + ", quantity=" + quantity + "]";
    }

    public Product() {
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}