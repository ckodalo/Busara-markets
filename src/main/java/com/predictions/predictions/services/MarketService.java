package com.predictions.predictions.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.predictions.predictions.models.User;
import com.predictions.predictions.models.dto.MarketDTO;
import com.predictions.predictions.models.dto.PredictionDetails;
import org.springframework.stereotype.Service;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.repositories.MarketRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarketService {

    private final MarketRepository marketRepository;

    private final PredictionService predictionService;

    private final ObjectMapper objectMapper;

    public MarketService(MarketRepository marketRepository, PredictionService predictionService, ObjectMapper objectMapper) {

        this.marketRepository = marketRepository;

        this.predictionService = predictionService;

        this.objectMapper = objectMapper;
    }

    public List<Market> getMarkets () {return marketRepository.findAll();}


    private MarketDTO appendMarketWithChartData (Market market) {

        MarketDTO marketDTO = new MarketDTO(market);

        List<PredictionDetails> chartData = predictionService.getPredictionsByMarket(market.getId(), market.getMarketType());


        String chartDataJson2 = null;
        try {
            chartDataJson2 = objectMapper.writeValueAsString(chartData);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }


        marketDTO.setChartDataJson2(chartDataJson2);

        return marketDTO;
    }

    public List<MarketDTO> marketDTOConverter (List<Market> markets) {

        List<MarketDTO> marketDTOS = new ArrayList<>();

        for (Market market : markets) {

            if (market.getMarketType().equals("YesNo")) {

                MarketDTO marketDTO = appendMarketWithChartData(market);

                marketDTOS.add(marketDTO);
            }

            else {

                MarketDTO marketDTO = new MarketDTO(market);
                marketDTOS.add(marketDTO);
            }
        }
        return marketDTOS;
    }

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

    public List<Market> getMarketsByUser (User user) {

        return marketRepository.findMarketsByUser(user);
    }
}
