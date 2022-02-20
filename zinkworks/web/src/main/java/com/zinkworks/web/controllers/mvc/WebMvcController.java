package com.zinkworks.web.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * WebMvcController class. Is used just for test web mvc page access.
 * It does not play any important role to the application purpose.
 */
@Controller
public class WebMvcController {

    //@Secured("ROLE_USER")
    @RequestMapping("/hello")
    public String hello(Model model) {
        // sets at the model the date attribute to just for the page index communication verification.
        model.addAttribute("currentTime", LocalDateTime.now());
        // the index page uses the thymeleaf templates.
        return "index";
    }

}
