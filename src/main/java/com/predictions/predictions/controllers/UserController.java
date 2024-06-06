package com.predictions.predictions.controllers;

import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.Prediction;
import com.predictions.predictions.models.User;
import com.predictions.predictions.models.dto.PredictionDetails;
import com.predictions.predictions.services.MarketService;
import com.predictions.predictions.services.PredictionService;
import com.predictions.predictions.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private  final MarketService marketService;

    private final PredictionService predictionService;

    public UserController (UserService userService, MarketService marketService, PredictionService predictionService) {

        this.userService = userService;
        this.marketService = marketService;
        this.predictionService = predictionService;
    }

    @GetMapping("/login")
    private String login () {

        return "fragments/login-modal";

    }


//    @PostMapping("/login")
//    private String login (@ModelAttribute User user) {
//
//        if (user == null) {
//
//            throw new RuntimeException("user cant be null");
//        }
//
//        try {
//
//            System.out.println("user detils is " + user);
//            userService.findUser(user);
//        }
//        catch (Exception e) {
//
//            throw new RuntimeException("user not found");
//        }
//
//        return "redirect:/markets";
//    }

    @GetMapping("signup")
    private String signup () {
        return "fragments/signup-modal";
    }

    @PostMapping("signup")
    private String signup (@ModelAttribute User user) {

     System.out.println(user);

     userService.createUser(user);

     return "redirect:/markets";
    }


    @GetMapping("profile")
    private String getProfile (@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails, Model model) {

        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        List<PredictionDetails> predictionsDetailsList = predictionService.getPredictionsByUser(user);

        PredictionDetails predictionDetails = new PredictionDetails();



        model.addAttribute("content", "profile");
//        model.addAttribute("marketsList", marketsList);
        model.addAttribute("user", user);
        model.addAttribute("predictionsDetailsList", predictionsDetailsList);

        return "layouts/app-layout";

    }

}
