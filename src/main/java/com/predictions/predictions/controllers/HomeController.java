package com.predictions.predictions.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Add any data you want to pass to the view
        model.addAttribute("pageTitle", "civic");
        return "home"; // Return the name of the Thymeleaf template (e.g., home.html)
    }
}
