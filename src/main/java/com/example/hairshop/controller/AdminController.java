package com.example.hairshop.controller;

import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.ShopService;
import com.example.hairshop.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final DesignerService designerService;
    private final ShopService shopService;
    private final CategoryService categoryService;
    private final StyleService styleService;

    /** 어드민 홈 **/
    @GetMapping("/admin")
    public String admin() {
        return "adminHome";
    }

}