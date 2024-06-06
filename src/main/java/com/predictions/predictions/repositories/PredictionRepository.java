package com.predictions.predictions.repositories;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    public Set<Prediction> findPredictionsBySecurityId (Long securityId);

    public List<Prediction> findPredictionsByUser(User user);

}
