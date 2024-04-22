package com.example.hairshop.controller;

import com.example.hairshop.domain.MyAround;
import com.example.hairshop.repository.MyAroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyAroundController {

    private final MyAroundRepository myAroundRepository;
    @GetMapping("/myAround")
    public String myAround(Model m) {
        List<MyAround> all = myAroundRepository.findAll();
        m.addAttribute("shops", all);

        return "/user/myAround";
    }
}
