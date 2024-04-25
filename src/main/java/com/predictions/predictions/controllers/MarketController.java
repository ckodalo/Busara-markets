package com.predictions.predictions.controllers;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.services.MarketService;
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

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

   @GetMapping()
    public String getMarkets(Model model) {

        List<Market> marketsList = marketService.getMarkets();

        //List<Security> securitiesList = marketService.getSecuritiesById

        // Iterate through the list of markets and print each market's details
        for (Market market : marketsList) {
            System.out.println("Market ID: " + market.getId());
            System.out.println("Market Title: " + market.getTitle());
            System.out.println("Market Description: " + market.getDescription());
            System.out.println("market Securities: " + market.getSecurities());
        }

        model.addAttribute("content", "markets");
        model.addAttribute("marketsList", marketsList);
        return "layouts/app-layout";
    }

    @GetMapping("markets/{marketId}")
    public Market getMarket(@PathVariable Long marketId) {
        return marketService.getMarketById(marketId);
    }

    @GetMapping("/{add_market}")
    public String addMarkets(Model model) {

        List<Market> marketsList = marketService.getMarkets();

        // Iterate through the list of markets and print each market's details
        for (Market market : marketsList) {
            System.out.println("Market ID: " + market.getId());
            System.out.println("Market Title: " + market.getTitle());
            System.out.println("Market Description: " + market.getDescription());
        }

        model.addAttribute("content", "add-markets");
        model.addAttribute("marketsList", marketsList);

        return "layouts/app-layout";
    }
    @PostMapping()
    public String createMarket(@ModelAttribute Market market,  @RequestParam("security") String[] securities) {

      System.out.println(market);

       List<Security> securitiesList = new ArrayList<>();

        for (String item : securities) {

            Security newSecurity = new Security();

            newSecurity.setName(item);

            newSecurity.setMarket(market);

            securitiesList.add(newSecurity);
        }

        market.setSecurities(securitiesList);
        marketService.createMarket(market);
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

//    @PostMapping("marketId")
//    public String makePrediction(@PathVariable Long markedId) {
//        marketService.makePrediction(marketId);
//        return "redirect:/markets";
//    }
}
