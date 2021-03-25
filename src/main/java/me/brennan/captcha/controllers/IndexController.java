package me.brennan.captcha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Brennan
 * @since 3/24/2021
 **/
@Controller
public class IndexController {

    @GetMapping
    public String homePage() {
        return "index";
    }

}
