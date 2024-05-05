package com.predictions.predictions.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Data
@Entity
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    private int probability;

    private boolean isOpen;

    @OneToMany(mappedBy = "security", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Prediction> predictions;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    public Security (String name, double price, int probability, Long id, Set<Prediction> predictions) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.isOpen = true;
        this.probability = getProbability(predictions);
    }

    public int getProbability(Set<Prediction> predictions) {

        //securityRepository.findById(security.getId());

        //Set<Prediction> predictions = security.getPredictions();

        int yesPredictions = 0;
        int predictionsCount = 0;

        for (Prediction prediction : predictions) {
            predictionsCount++;
            if (prediction.getValue().equals("Yes")) {

                yesPredictions++;
            };
        }

        return (yesPredictions / predictionsCount) * 100;
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
