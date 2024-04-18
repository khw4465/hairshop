package com.example.hairshop.controller;

import com.example.hairshop.domain.Shop;
import com.example.hairshop.domain.Style;
import com.example.hairshop.dto.ShopDto;
import com.example.hairshop.dto.StyleDto;
import com.example.hairshop.service.ShopService;
import com.example.hairshop.service.StyleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ShopService shopService;
    private final StyleService styleService;

    @GetMapping("/")
    public String home(Model m) {
        List<ShopDto> shopAll = shopService.findRandomPageAll(0, 8);
        m.addAttribute("shops", shopAll);

        List<Style> styleAll = styleService.findAll();
        List<StyleDto> styleList = styleAll.stream().map(StyleDto::new).toList();
        m.addAttribute("styles", styleList);

        return "home";
    }
}