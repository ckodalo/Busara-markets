package com.predictions.predictions.repositories;

import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Long> {


    List<Market> findByTitleContainingIgnoreCase(String search);

    List<Market> findMarketsByUser(User user);
}
