package org.oauth2client2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/success-logout")
    public String successLogout(Model model) {
        model.addAttribute(
                "message",
                "YOUR LOGOUT WAS SUCCESSFUL!"
        );
        return "success-logout";
    }

    @GetMapping("/without-consent-logout")
    public String withoutConsentLogout(Model model) {
        model.addAttribute(
                "message",
                "YOUR CONSENT WAS REMOVED!"
        );
        return "success-logout";
    }
}
