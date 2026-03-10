package com.example.Model;

public class Supplier {
    private int id;
    private String name;
    private String description;
    private String contact;
    private String address;

    public Supplier(int id, String name, String description, String contact, String address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contact = contact;
        this.address = address;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
