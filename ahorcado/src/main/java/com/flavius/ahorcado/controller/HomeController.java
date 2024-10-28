package com.flavius.ahorcado.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @GetMapping("/") // This maps the root URL
    public RedirectView redirectToLogin() {
        return new RedirectView("/acceso/login"); // Redirects to /acceso/login
    }
}