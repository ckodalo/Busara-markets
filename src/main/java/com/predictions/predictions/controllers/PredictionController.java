package com.predictions.predictions.controllers;

import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.models.dto.PredictionForm;
import com.predictions.predictions.services.MarketService;
import com.predictions.predictions.services.PredictionService;
import com.predictions.predictions.services.SecurityService;
import com.predictions.predictions.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class PredictionController {

    private final PredictionService predictionService;

    private final SecurityService securityService;

    private final UserService userService;

    private  final MarketService marketService;

    public PredictionController (PredictionService predictionService, SecurityService securityService, MarketService marketService, UserService userService) {

        this.predictionService = predictionService;

        this.securityService = securityService;

        this.marketService = marketService;

        this.userService = userService;
    }

    //this is same as buying share,, what about selling share

    @PostMapping("/predict")
    public String predict(@AuthenticationPrincipal User userDetails, @ModelAttribute Security security, @RequestParam String prediction, @RequestParam Long marketId, @RequestParam int nShares ) {

        Long securityId = security.getId();

        Prediction newPrediction = new Prediction();

        newPrediction.setValue(prediction);

        newPrediction.setNShares(nShares);

        newPrediction.setSecurity(security);

        String username = userDetails.getUsername();

        newPrediction.setUser(userService.findByUsername(username));

        predictionService.makePrediction(newPrediction);

        marketService.doThings(marketId, securityId, nShares);

        return "redirect:/markets";
    }
}
