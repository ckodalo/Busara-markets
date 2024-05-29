package com.predictions.predictions.services;

import org.springframework.stereotype.Service;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.repositories.MarketRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MarketService {

    private final MarketRepository marketRepository;

    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public List<Market> getMarkets () {return marketRepository.findAll();}

    public List<Market> searchMarkets(String search) {
        return marketRepository.findByTitleContainingIgnoreCase(search);
    }
    public Market getMarketById(Long marketId) {
        return marketRepository.findById(marketId).orElse(null);
    }

    public void createMarket(Market market) {

        marketRepository.save(market);
    }

    public Market addSecurityToMarket(Long marketId, Security security) {
        Market market = marketRepository.findById(marketId).orElse(null);
        if (market != null) {
            market.addSecurity(security);
            marketRepository.save(market);
        }
        return market;
    }

    public void deleteMarket(Market market) {

      marketRepository.delete(market);
    }

    public void deleteMarketById(Long marketId) {

        marketRepository.deleteById(marketId);
    }


    public Market findMarketById (Long marketId) {

        Optional<Market> possibleMarket = marketRepository.findById(marketId);

        return possibleMarket.orElse(null);

    }

    // method to set new probability and price

    public void doThings (Long marketId, Long securityId, int nShares) {

        Market targetMarket = findMarketById(marketId);

        List<Security> marketSecurities = targetMarket.getSecurities();

//        targetMarket.calculateShareCost(marketSecurities);

        targetMarket.calculateTransactionCost(marketSecurities, nShares, securityId);

        targetMarket.calculateProbabilities(marketSecurities);



        marketRepository.save(targetMarket);

    }
}
