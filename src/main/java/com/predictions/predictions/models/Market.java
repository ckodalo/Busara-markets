package com.predictions.predictions.models;

import java.util.List;
import java.util.ArrayList;

public class Market {

    private String title;
    private String description;
    private List<Security> securities;

    public Market (String title, String description) {

        this.title = title;
        this.description = description;
        this.securities = new ArrayList<>();

    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Security> getSecurities() {
        return securities;
    }

    public void setSecurities(List<Security> securities) {
        this.securities = securities;
    }

    public void addSecurity(Security security) {
        securities.add(security);
    }

    public void removeSecurity(Security security) {
        securities.remove(security);
    }
}
