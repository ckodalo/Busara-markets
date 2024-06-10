package com.predictions.predictions.services;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.models.User;
import com.predictions.predictions.models.dto.PredictionDetails;
import com.predictions.predictions.repositories.PredictionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PredictionService {

    private final PredictionRepository predictionRepository;


    public PredictionService (PredictionRepository predictionRepository) {

        this.predictionRepository = predictionRepository;
    }

    public void makePrediction (Prediction prediction) {

        predictionRepository.save(prediction);
    }

    public List<PredictionDetails> getPredictionsByUser (User user) {

        List<PredictionDetails> result = new ArrayList<>();

       List<Prediction> predictions = predictionRepository.findPredictionsByUser(user);

       for (Prediction prediction : predictions) {

           Security security = prediction.getSecurity();

           Market market = security.getMarket();

           LocalDateTime timestamp = prediction.getTimestamp();

           DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
           LocalDateTime dateTime = LocalDateTime.parse(timestamp.toString(), inputFormatter);

           DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
           String formattedDate = dateTime.format(outputFormatter);


           PredictionDetails predictionDetails = new PredictionDetails();

           predictionDetails.setShares(prediction.getNShares());
           predictionDetails.setLastPredictionDate(formattedDate);

           predictionDetails.setClosingDate(market.getClosingDate());

           predictionDetails.setMarketTitle(market.getTitle());

           predictionDetails.setSecurityName(security.getName());

           result.add(predictionDetails);
       }
         return result;
    }

    public List<PredictionDetails> getPredictionsByMarket (Long marketId, String marketType) {

       List<PredictionDetails> result = new ArrayList<>();


       //replace this with predictionRepository.findPredictionsByMarketId(marketId);
       List<Prediction> allPredictions = predictionRepository.findAll();

       List<Prediction> predictions = allPredictions.stream()
                .filter(prediction -> prediction.getSecurity().getMarket().getId().equals(marketId))
                .toList();

       List<Prediction> predictionsByMarketType = predictions.stream()
               .filter(prediction -> prediction.getSecurity().getMarket().getMarketType().equals(marketType))
               .toList();


       for (Prediction prediction : predictionsByMarketType) {

           Security security = prediction.getSecurity();

           Market market = security.getMarket();

           double prob = prediction.getProbability();

           LocalDateTime timestamp = prediction.getTimestamp();

           DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
           LocalDateTime dateTime = LocalDateTime.parse(timestamp.toString(), inputFormatter);

           DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
           String formattedDate = dateTime.format(outputFormatter);


           PredictionDetails predictionDetails = new PredictionDetails();

           predictionDetails.setShares(prediction.getNShares());
           predictionDetails.setLastPredictionDate(formattedDate);

           predictionDetails.setProbability((int) prob);
           predictionDetails.setClosingDate(market.getClosingDate());

           predictionDetails.setMarketTitle(market.getTitle());

           predictionDetails.setSecurityName(security.getName());

           result.add(predictionDetails);
       }

       return result;
    }

}
