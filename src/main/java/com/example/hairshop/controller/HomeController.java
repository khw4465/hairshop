package com.example.hairshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        Object id = session.getAttribute("userId");
        System.out.println("id = " + id);

        if (id != null) {
            mav.addObject(session.getAttribute("userId"));
        }
        mav.setViewName("home");

        return mav;
    }

    @GetMapping("/redirect")
    public ModelAndView redirect(HttpSession session) {


        ModelAndView mav = new ModelAndView();

        Object userId = session.getAttribute("userId");
        mav.addObject(userId);
        mav.setViewName("home");

        return mav;
    }
}