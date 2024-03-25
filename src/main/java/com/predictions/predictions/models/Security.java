package com.predictions.predictions.models;

public class Security {

    private String name;
    private double price;
    private boolean isOpen;

    public Security (String name, double price) {

        this.name = name;
        this.price = price;
        this.isOpen = true;
    }

    // Getters and setters
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
