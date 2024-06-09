package com.predictions.predictions.controllers;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;

import com.predictions.predictions.models.dto.PredictionDetails;
import com.predictions.predictions.services.*;
import com.predictions.predictions.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/markets")
public class MarketController {

    private final MarketService marketService;

    private final SecurityService securityService;

    private  final UserService userService;

    private final PredictionService predictionService;

    //private final

    public MarketController(MarketService marketService, SecurityService securityService, UserService userService, PredictionService predictionService) {
        this.marketService = marketService;

        this.securityService = securityService;

        this.userService = userService;

        this.predictionService = predictionService;
    }

   @GetMapping()
    public String getMarkets(@RequestParam(value = "search", required = false) String search, Model model) {

        List<Market> marketsList;

        if (search != null && !search.isEmpty()) {

            marketsList = marketService.searchMarkets(search);
        }
        else {
        marketsList = marketService.getMarkets();

       }

       model.addAttribute("content", "markets");
       model.addAttribute("marketsList", marketsList);

       return "layouts/app-layout";
    }

    @GetMapping("{marketId}")
    public String getMarket(Model model, @PathVariable Long marketId) {

        Market targetMarket = marketService.getMarketById(marketId);

        List<PredictionDetails> chartData = predictionService.getPredictionsByMarket(marketId);

        System.out.println(chartData);


        model.addAttribute("chartData", chartData);



        model.addAttribute("content", "market");

        model.addAttribute("market", targetMarket);

        return "layouts/app-layout";

    }

    @GetMapping("/create")
    public String create(Model model) {

        List<Market> marketsList = marketService.getMarkets();

        model.addAttribute("content", "create");
//        model.addAttribute("marketsList", marketsList)

        return "layouts/app-layout";
    }
    @PostMapping("/create")
    public String createMarket(@AuthenticationPrincipal User userDetails, @ModelAttribute Market market, @RequestParam("security") String[] securities) {

       List<Security> securitiesList = new ArrayList<>();

       String username = userDetails.getUsername();

       market.setUser(userService.findByUsername(username));

       marketService.createMarket(market);

        for (String item : securities) {

            Security newSecurity = new Security();

            newSecurity.setName(item);

            newSecurity.setMarket(market);

            securityService.saveSecurity(newSecurity);

            securitiesList.add(newSecurity);
        }

        market.setSecurities(securitiesList);

        return "redirect:/markets";
    }

    @PostMapping("/{marketId}/securities")
    public Market addSecurityToMarket(@PathVariable Long marketId, @RequestBody Security security) {
        return marketService.addSecurityToMarket(marketId, security);
    }

    @PostMapping("{marketId}")
    public String deleteMarketById(@PathVariable Long marketId) {
        marketService.deleteMarketById(marketId);
        return "redirect:/markets";
    }

}
