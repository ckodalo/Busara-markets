package com.predictions.predictions.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private String marketType;

    private double liquidity = 100.0;

    @OneToMany(mappedBy = "market", fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Security> securities;

    public Market (String title, String description, List<Security> securities, String marketType, double liquidity) {

        this.title = title;
        this.description = description;
        //this.securities = new ArrayList<>();

        this.marketType = marketType;

        this.securities = securities;

         this.liquidity = liquidity;

        System.out.println("Setting liquidity parameter = " + this.liquidity);


        System.out.println("Initial number of shares owned");
        for (Security security: securities) {

            System.out.println("for" + security.getName() + "is 0.0");

        }


        System.out.println("current costs for one share of each team are");
        for (Security security: securities) {

            calculateShareCost(securities);

            System.out.println("for" + security.getName() + "is 0.0");

        }

    }

    public void addSecurity(Security security) {
        securities.add(security);
    }

    public void removeSecurity(Security security) {
        securities.remove(security);
    }


    public double calculateCost (List<Security> securities) {


        System.out.println("liwudity is " + this.liquidity);

        double sum = 0.0;

        for (Security security: securities) {

            sum += Math.exp(security.getQuantity() / this.liquidity);
        }

        double result = this.liquidity * Math.log(sum);

        System.out.println("result in calculcaeCost is " + result);

        return result;
    }

    public double calculateTransactionCost (List<Security> securities, int nShares, Long securityId) {

        List<Security> newSecurities = new ArrayList<>(securities);

        for (Security security : newSecurities) {

            if (Objects.equals(security.getId(), securityId)) {

                double newQuantity = security.getQuantity() + nShares;

                System.out.println("newQuantity in calculcaeTransactionCost is " + newQuantity);

                security.setQuantity(newQuantity);
            }
        }

        double transactionCost = calculateCost(newSecurities) - calculateCost(securities);

        System.out.println("cost of transaction is" + transactionCost);

        return transactionCost;
    }

    //method to calculate the cost of a single share
    public void calculateShareCost (List<Security> securities) {

        for (Security security : securities) {

            double shareCost = calculateTransactionCost(securities, 1, security.getId());

            security.setPrice(shareCost);
            System.out.println("current costs for one share of" + security.getName() + "is" + shareCost );
        }
    }

    public void calculateProbabilities (List<Security> securities) {

        double denom = 0.0;

        for (Security security : securities) {

            denom += Math.exp(security.getQuantity() / liquidity);

            System.out.println("denom in calculcaeProbailities is " + denom);

        }

        for (Security security : securities) {

            double probability = Math.exp(security.getQuantity() / liquidity) / denom;
            security.setProbability(Math.round(probability * 100));



            System.out.println("current probability of " + security.getName() + "winning is" + security.getProbability());
        }

    }


}
