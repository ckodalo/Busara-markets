package com.predictions.predictions.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.Set;

@Data
@Entity
//@NoArgsConstructor
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //this is cost parameter.. lol no
    private double price;

    private double quantity;

    //latest probability
    private double probability;

    private boolean isOpen;

    private String color;

    @OneToMany(mappedBy = "security", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Prediction> predictions;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    public Security () {

        this.color = generateRandomColor();
    }

    public Security (String name, double price, int probability, Long id, Set<Prediction> predictions, double quantity) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isOpen = true;
        this.probability = getProbability(predictions);
        this.color = generateRandomColor();
    }

    public int getProbability(Set<Prediction> predictions) {

        //securityRepository.findById(security.getId());

        //Set<Prediction> predictions = security.getPredictions();

        int yesPredictions = 0;
        int predictionsCount = 0;

        for (Prediction prediction : predictions) {
            predictionsCount++;
            if (prediction.getAction().toString().equals("YES")) {

                yesPredictions++;
            };
        }

        return (yesPredictions / predictionsCount) * 100;
    }

    private String generateRandomColor () {

        // Create a new instance of the Random class to generate random numbers
        Random random = new Random();

        // Generate a random integer between 0 and 0xFFFFFF (inclusive).
        // 0xFFFFFF is the hexadecimal representation of 16777215, which is the highest value for a 24-bit color.
        int nextInt = random.nextInt(0xffffff + 1);

        System.out.println("nextInt generated for color is :" + nextInt);

        // Format the random integer as a 6-digit hexadecimal string.
        // %06x: This is a format specifier for printf-style formatting.
        // %: indicates the start of a format specifier.
        // 06: ensures that the number is zero-padded to 6 digits.
        // x: converts the number to a hexadecimal string.
        return String.format("#%06x", nextInt);
    }

}
