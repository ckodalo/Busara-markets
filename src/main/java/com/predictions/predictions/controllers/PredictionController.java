package com.predictions.predictions.controllers;

import com.predictions.predictions.enums.TradeAction;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;
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
    public String predict(@AuthenticationPrincipal User userDetails, @ModelAttribute Security security, @RequestParam Long marketId, @RequestParam int nShares, @RequestParam String action ) {

        System.out.println("the action received is " + action);

        if (action.equals("NO")) {

           nShares = -nShares;
        }

        System.out.println("the adjusted nShares is " + nShares);

        Long securityId = security.getId();

        Prediction newPrediction = new Prediction();

        newPrediction.setAction(TradeAction.valueOf(action));

        newPrediction.setNShares(nShares);

        newPrediction.setSecurity(security);

        String username = userDetails.getUsername();

        newPrediction.setUser(userService.findByUsername(username));

//        newPrediction.setProbability(security.getProbability());

        marketService.doThings(marketId, securityId, nShares);

        double prob = securityService.getSecurityById(securityId).getProbability();

        newPrediction.setProbability(prob);

        predictionService.makePrediction(newPrediction);

        return "redirect:/markets";
    }
}
