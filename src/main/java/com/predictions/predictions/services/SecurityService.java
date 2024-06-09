package com.predictions.predictions.services;

import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.repositories.PredictionRepository;
import com.predictions.predictions.repositories.SecurityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SecurityService {

    private final SecurityRepository securityRepository;

    private final PredictionRepository predictionRepository;

    public SecurityService (SecurityRepository securityRepository, PredictionRepository predictionRepository) {

        this.securityRepository = securityRepository;

        this.predictionRepository = predictionRepository;
    }


    public void saveSecurity(Security security) {

        securityRepository.save(security);
    }

    public Security getSecurityById (Long securityId) {

        Optional<Security> possibleSecurity = securityRepository.findById(securityId);

        if (possibleSecurity.isPresent()) {
            return possibleSecurity.get();
        }
        else {

            throw new RuntimeException("there is no security with the provided id in the db");
        }
    }

    public int getProbability(Long securityId) {

        Set<Prediction> predictions = predictionRepository.findPredictionsBySecurityId(securityId);

        int yesPredictions = 0;
        int predictionsCount = 0;

        for (Prediction prediction : predictions) {
            System.out.println("we are not isnde the predicins loop");
            System.out.println("prediction id: " + prediction.getId());
             predictionsCount++;
            if (prediction.getAction().toString().equals("YES")) {

                yesPredictions++;
            };
        }
        System.out.println("yesPredictions: " + yesPredictions);
        System.out.println("preductionsCount: " + predictionsCount);

        double probability = ((double) yesPredictions / predictionsCount) * 100;

        System.out.println(probability);
        return (int) probability;
    }

}
