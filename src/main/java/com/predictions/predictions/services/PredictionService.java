package com.predictions.predictions.services;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.models.User;
import com.predictions.predictions.models.dto.PredictionDetails;
import com.predictions.predictions.repositories.PredictionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

           PredictionDetails predictionDetails = new PredictionDetails();

           predictionDetails.setShares(prediction.getNShares());
           predictionDetails.setLastPredictionDate(String.valueOf(prediction.getTimestamp()));

           predictionDetails.setClosingDate(market.getClosingDate());

           predictionDetails.setMarketTitle(market.getTitle());

           predictionDetails.setSecurityName(security.getName());

           result.add(predictionDetails);
       }
         return result;
    }

}
