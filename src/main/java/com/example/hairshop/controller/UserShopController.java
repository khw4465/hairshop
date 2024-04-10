package com.example.hairshop.controller;

import com.example.hairshop.domain.Shop;
import com.example.hairshop.dto.ShopDto;
import com.example.hairshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserShopController {

    private final ShopService shopService;

//    @GetMapping("/user/shop/list")
//    private String shopCategoryList(@RequestParam("categoryName") String categoryName, Model m) {
//
//        List<Shop> shop = shopService.findByCategory(categoryName);
//        List<ShopDto> list = shop.stream()
//                .map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(), s.getShopImgs().stream()
//                        .map(i -> i.getImgUrl()).toList())).toList();
//
//        m.addAttribute("shopList", list);
//
//        return "/user/shopList";
//    }
}
