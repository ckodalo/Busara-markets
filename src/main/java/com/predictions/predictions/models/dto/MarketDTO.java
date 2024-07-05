package com.predictions.predictions.models.dto;

import com.predictions.predictions.models.Comment;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Security;
import com.predictions.predictions.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MarketDTO {


      private Market market;
//    private Long id;
//
//    private String title;
//    private String description;
//
//    private String category;
//
//    private String closingDate;
//
//    private String marketType;
//
//    private double liquidity = 100.0;
//
//    private String securityName;
      private String marketTitle;
//    private int shares;
//    private String lastPredictionDate;
//
//    private int probability;

    String marketType;

    String chartDataJson2;

    Long marketId;

    List<Security> marketSecurities;

    String marketCategory;

    double marketLiquidity;

    String marketClosingDate;

    String marketDescription;

    List<Comment> marketComments;

    User marketUser;

   public MarketDTO(Market market) {


       this.marketType = market.getMarketType();

       this.marketId = market.getId();

       this.marketSecurities = market.getSecurities();

       this.marketTitle = market.getTitle();

       this.marketCategory = market.getCategory();

       this.marketLiquidity = market.getLiquidity();

       this.marketClosingDate = market.getClosingDate();

       this.marketDescription = market.getDescription();

       this.marketComments = market.getComments();

       this.marketUser = market.getUser();

   }

}
