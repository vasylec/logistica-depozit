package com.example.Model;

public class Warehouse {
    private int id;
    private String nume;
    private String locatie;
    private int capacitate;

    public Warehouse(int id, String nume, String locatie, int capacitate) {
        this.id = id;
        this.nume = nume;
        this.locatie = locatie;
        this.capacitate = capacitate;
    }

    public Warehouse(String nume, String locatie, int capacitate) {
        this.nume = nume;
        this.locatie = locatie;
        this.capacitate = capacitate;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public int getId() {
        return id;
    }

    public String getLocatie() {
        return locatie;
    }

    public String getNume() {
        return nume;
    }

    public void setCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }
}
