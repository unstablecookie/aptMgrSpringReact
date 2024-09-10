package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    
    @GetMapping("")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }



    @GetMapping("sad")
    public String fooSad() {
        return ":(";
    }
}
