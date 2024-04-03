package com.predictions.predictions.services;

import org.springframework.stereotype.Service;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.repositories.MarketRepository;

import java.util.List;

@Service
public class MarketService {


    private final MarketRepository marketRepository;

    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public List<Market> getMarkets () {return marketRepository.findAll();}
    public Market getMarketById(Long marketId) {
        return marketRepository.findById(marketId).orElse(null);
    }

    public Market createMarket(Market market) {
        // Additional validation logic can be added here
        return marketRepository.save(market);
    }

    public Market addSecurityToMarket(Long marketId, Security security) {
        Market market = marketRepository.findById(marketId).orElse(null);
        if (market != null) {
            market.addSecurity(security);
            marketRepository.save(market);
        }
        return market;
    }

}
