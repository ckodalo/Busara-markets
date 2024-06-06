package com.predictions.predictions.models.dto;

import lombok.Data;


@Data
public class PredictionDetails {

    //no financial aspect yet
    private String securityName;
    private String marketTitle;
    private int shares;
    private String lastPredictionDate;
    private String closingDate;
    private int probability;

}
