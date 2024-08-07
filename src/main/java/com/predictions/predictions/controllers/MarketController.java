package com.predictions.predictions.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.predictions.predictions.models.Comment;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.Security;

import com.predictions.predictions.models.dto.MarketDTO;
import com.predictions.predictions.models.dto.PredictionDetails;
import com.predictions.predictions.services.*;
import com.predictions.predictions.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/markets")
public class MarketController {

    private final MarketService marketService;

    private final SecurityService securityService;

    private  final UserService userService;

    private final PredictionService predictionService;

    private final CommentService commentService;

    private final ObjectMapper objectMapper;

    //private final

    public MarketController(MarketService marketService, SecurityService securityService, UserService userService, PredictionService predictionService, CommentService commentService, ObjectMapper objectMapper) {
        this.marketService = marketService;

        this.securityService = securityService;

        this.userService = userService;

        this.predictionService = predictionService;

        this.commentService = commentService;

        this.objectMapper = objectMapper;
    }

   @GetMapping()
    public String getMarkets(@RequestParam(value = "search", required = false) String search, Model model) {

        List<Market> marketsList;

        if (search != null && !search.isEmpty()) {

            marketsList = marketService.searchMarkets(search);
        }
        else {
        marketsList = marketService.getMarkets();

       }

        List<MarketDTO> marketDTOS = marketService.marketDTOConverter(marketsList);


       model.addAttribute("content", "markets");
       model.addAttribute("marketsList", marketDTOS);

       return "layouts/app-layout";
    }

    @GetMapping("{marketId}")
    public String getMarket(Model model, @PathVariable Long marketId, @RequestParam("marketType") String marketType) throws Exception {

        Market targetMarket = marketService.getMarketById(marketId);

        if (marketType.equals("YesNo")) {

             List<PredictionDetails> YesNoPredictionDetails = predictionService.getPredictionsByMarket(marketId, marketType);

            List<PredictionDetails> YesPredictionDetails = YesNoPredictionDetails.stream()
                    .filter(prediction -> prediction.getSecurityName().equals("YES"))
                    .toList();

             String chartDataJson2 = objectMapper.writeValueAsString(YesPredictionDetails);

             model.addAttribute("chartDataJson2", chartDataJson2);
        }

        else {

            List<PredictionDetails> chartData = predictionService.getPredictionsByMarket(marketId, marketType);

            System.out.println(chartData);

            String chartDataJson = objectMapper.writeValueAsString(chartData);

            model.addAttribute("chartDataJson", chartDataJson);

        }

       List<Comment> comments = commentService.getCommentsForMarket(marketId);

        for (Comment comment : comments) {

            System.out.println("comment content is" + comment.getContent());

            System.out.println("comment author us " + comment.getAuthor());

            for (Comment reply : comment.getReplies()) {

                System.out.println("reply content is " + reply.getContent());
            }
        }

        model.addAttribute("comments", comments);

        model.addAttribute("content", "market");

        model.addAttribute("market", targetMarket);

        return "layouts/app-layout";

    }

    @GetMapping("/create")
    public String create(Model model) {

        List<Market> marketsList = marketService.getMarkets();

        model.addAttribute("content", "create");
//        model.addAttribute("marketsList", marketsList)

        return "layouts/app-layout";
    }
    @PostMapping("/create")
    public String createMarket(@AuthenticationPrincipal User userDetails, @ModelAttribute Market market, @RequestParam("security") String[] securities) {

       List<Security> securitiesList = new ArrayList<>();

       String username = userDetails.getUsername();

       market.setUser(userService.findByUsername(username));

       marketService.createMarket(market);

        for (String item : securities) {

            Security newSecurity = new Security();

            newSecurity.setName(item);

            newSecurity.setMarket(market);

            securityService.saveSecurity(newSecurity);

            securitiesList.add(newSecurity);
        }

        market.setSecurities(securitiesList);

        return "redirect:/markets";
    }

    @PostMapping("/{marketId}/securities")
    public Market addSecurityToMarket(@PathVariable Long marketId, @RequestBody Security security) {
        return marketService.addSecurityToMarket(marketId, security);
    }

    @PostMapping("{marketId}")
    public String deleteMarketById(@PathVariable Long marketId) {
        marketService.deleteMarketById(marketId);
        return "redirect:/markets";
    }


}
