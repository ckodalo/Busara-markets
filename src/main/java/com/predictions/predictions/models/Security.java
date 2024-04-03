package com.predictions.predictions.models;

import jakarta.persistence.*;

@Entity
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private boolean isOpen;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    public Security (String name, double price, Long id) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.isOpen = true;
    }

    public Security () {}

    // Getters and setters

    public  Long getId() {return id;}

    public void setId(Long id) {

        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
