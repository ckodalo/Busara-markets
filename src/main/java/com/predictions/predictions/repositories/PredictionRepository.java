package com.predictions.predictions.repositories;
import com.predictions.predictions.models.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {


//    public PredictionRepository () {
//
//
//    }
//    public void makePrediction (Prediction prediction) {
//
//
//    }

    public Set<Prediction> findPredictionsBySecurityId (Long securityId);

}
