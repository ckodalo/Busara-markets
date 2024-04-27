package com.predictions.predictions.services;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.repositories.PredictionRepository;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    private final PredictionRepository predictionRepository;


    public PredictionService (PredictionRepository predictionRepository) {

        this.predictionRepository = predictionRepository;
    }

    public void makePrediction (Prediction prediction) {

        predictionRepository.save(prediction);
    }

}
