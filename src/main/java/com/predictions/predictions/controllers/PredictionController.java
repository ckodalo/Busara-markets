package com.predictions.predictions.controllers;

import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.services.PredictionService;
import com.predictions.predictions.services.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class PredictionController {

    private final PredictionService predictionService;

    private final SecurityService securityService;

    public PredictionController (PredictionService predictionService, SecurityService securityService) {

        this.predictionService = predictionService;

        this.securityService = securityService;
    }

    @PostMapping("/predict")
    public String predict(@ModelAttribute Security security, @RequestParam String prediction) {

        securityService.saveSecurity(security);

        Prediction newPrediction = new Prediction();


        newPrediction.setValue(prediction);

        newPrediction.setSecurity(security);

        predictionService.makePrediction(newPrediction);

        return "redirect:/markets";
    }
}
