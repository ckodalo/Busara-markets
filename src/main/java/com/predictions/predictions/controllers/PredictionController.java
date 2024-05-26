package com.predictions.predictions.controllers;

import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.models.dto.PredictionForm;
import com.predictions.predictions.services.MarketService;
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

    private  final MarketService marketService;

    public PredictionController (PredictionService predictionService, SecurityService securityService, MarketService marketService) {

        this.predictionService = predictionService;

        this.securityService = securityService;

        this.marketService = marketService;
    }

    //this is same as buying share,, what about selling share

    // this method should take nshares
    @PostMapping("/predict")
    public String predict(@ModelAttribute Security security, @RequestParam String prediction, @RequestParam Long marketId, @RequestParam int nShares ) {

        Long securityId = security.getId();

        String securityName = security.getName();

        System.out.println("nshares in controller is: " + nShares);
        System.out.println("security id in controller is: " + securityId);
        System.out.println("security name in controller is: " + securityName);
        System.out.println("market id in the given security is: " + marketId);

        Prediction newPrediction = new Prediction();

        newPrediction.setValue(prediction);

        newPrediction.setSecurity(security);

        predictionService.makePrediction(newPrediction);

        marketService.doThings(marketId, securityId, nShares);

        return "redirect:/markets";
    }
}
