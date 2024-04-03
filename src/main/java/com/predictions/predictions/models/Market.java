package com.predictions.predictions.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Security> securities;

    public Market (String title, String description, Long id) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.securities = new ArrayList<>();

    }

    public Market () {}

    // Getters and

    public  Long getId() {return id;}

    public void setId(Long id) {

        this.id = id;
    }
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
