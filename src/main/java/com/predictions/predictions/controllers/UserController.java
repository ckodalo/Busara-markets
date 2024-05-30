package com.predictions.predictions.controllers;

import com.predictions.predictions.models.User;
import com.predictions.predictions.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


    @PostMapping("/login")
    private String login (@ModelAttribute User user) {

        try {
            userService.findUser(user);
        }
        catch (Exception e) {

            throw new RuntimeException();
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
