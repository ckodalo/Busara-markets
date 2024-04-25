package com.predictions.predictions.models;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;
//import jakarta.transaction.Transactional;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private String marketType;
    @OneToMany(mappedBy = "market", fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Security> securities;


    public Market (String title, String description, List<Security> securities, String marketType) {

        this.title = title;
        this.description = description;
        //this.securities = new ArrayList<>();

        this.marketType = marketType;

        this.securities = securities;



        //Security newSecurity =new Security();
        //newSecurity.setName(security);

        //this.securities.add(newSecurity);
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
