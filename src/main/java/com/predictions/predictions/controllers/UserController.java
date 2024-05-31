package com.predictions.predictions.controllers;

import com.predictions.predictions.models.User;
import com.predictions.predictions.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController (UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/login")
    private String login () {

        return "fragments/login-modal";

    }


    @PostMapping("/login")
    private String login (@ModelAttribute User user) {

        if (user == null) {

            throw new RuntimeException("user cant be null");
        }

        try {

            System.out.println("user detils is " + user);
            userService.findUser(user);
        }
        catch (Exception e) {

            throw new RuntimeException("user not found");
        }

        return "redirect:/markets";
    }

    @PostMapping("signup")
    private String signup (@ModelAttribute User user) {

     System.out.println(user);

     userService.createUser(user);

     return "redirect:/markets";
    }

}
