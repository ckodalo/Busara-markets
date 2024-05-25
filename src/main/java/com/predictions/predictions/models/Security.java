package com.predictions.predictions.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //this is cost parameter.. lol no
    private double price;

    private double quantity;

    private double probability;

    private boolean isOpen;

    @OneToMany(mappedBy = "security", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Prediction> predictions;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    public Security (String name, double price, int probability, Long id, Set<Prediction> predictions, double quantity) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

}
