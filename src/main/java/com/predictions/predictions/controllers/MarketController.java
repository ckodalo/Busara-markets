package com.predictions.predictions.controllers;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.services.MarketService;
import com.predictions.predictions.services.SecurityService;
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

    public MarketController(MarketService marketService, SecurityService securityService) {
        this.marketService = marketService;

        this.securityService = securityService;
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
//        model.addAttribute("security", new Security());

       return "layouts/app-layout";
    }

    @GetMapping("{marketId}")
    public String getMarket(Model model, @PathVariable Long marketId) {

        Market targetMarket = marketService.getMarketById(marketId);

        model.addAttribute("content", "market");

        model.addAttribute("market", targetMarket);

        return "layouts/app-layout";

    }

    @GetMapping("/create")
    public String create(Model model) {

        List<Market> marketsList = marketService.getMarkets();

        model.addAttribute("content", "create");
//        model.addAttribute("marketsList", marketsList);

        return "layouts/app-layout";
    }
    @PostMapping("/create")
    public String createMarket(@ModelAttribute Market market,  @RequestParam("security") String[] securities) {

        System.out.println("entered create action");

        System.out.println("entire market: " + market);

      System.out.println("just the market id : " + market.getId());

       List<Security> securitiesList = new ArrayList<>();

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

//    @PostMapping("predict")
//    public String predict(@ModelAttribute Security security, @PathVariable String prediction) {
//
//            Prediction newPrediction = new Prediction();
//
//            newPrediction.setValue(prediction);
//
//            newPrediction.setSecurity(security);
//
//        marketService.makePrediction(newPrediction);
//
//        return "redirect:/markets";
//    }
}
