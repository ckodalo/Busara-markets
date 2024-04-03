package com.predictions.predictions.controllers;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.services.MarketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        List<Market> markets = marketService.getMarkets();

        // Iterate through the list of markets and print each market's details
        for (Market market : markets) {
            System.out.println("Market ID: " + market.getId());
            System.out.println("Market Title: " + market.getTitle());
            System.out.println("Market Description: " + market.getDescription());
        }


        model.addAttribute("markets", markets);
        return "markets";
    }

    @GetMapping("/{marketId}")
    public Market getMarket(@PathVariable Long marketId) {
        return marketService.getMarketById(marketId);
    }

    @PostMapping
    public String createMarket(@ModelAttribute Market market) {
        marketService.createMarket(market);
        return "redirect:/markets";
    }

    @PostMapping("/{marketId}/securities")
    public Market addSecurityToMarket(@PathVariable Long marketId, @RequestBody Security security) {
        return marketService.addSecurityToMarket(marketId, security);
    }
}
