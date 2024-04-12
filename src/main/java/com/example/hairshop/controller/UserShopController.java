package com.example.hairshop.controller;

import com.example.hairshop.domain.Shop;
import com.example.hairshop.dto.ShopDto;
import com.example.hairshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserShopController {

    private final ShopService shopService;

    /** 상단 헤더 카테고리(페이징) **/
    @GetMapping("/shop/list")
    public String shopCategoryList(@RequestParam("categoryName") String categoryName,
                                    @RequestParam(value = "offset", defaultValue = "0") int offset,
                                    @RequestParam(value = "limit", defaultValue = "9") int limit,
                                    Model m) {
        List<ShopDto> result = shopService.findPageByCategory(categoryName, offset, limit);
        long count = shopService.countQueryByCategory(categoryName);

        m.addAttribute("shopList", result);
        m.addAttribute("count", count);
        m.addAttribute("offset", offset);
        m.addAttribute("limit", limit);
        m.addAttribute("searchText", categoryName);

        return "/user/shopList";
    }

    /** 샵 상세정보 **/
    @GetMapping("/shop/info")
    public String shopInfo(@RequestParam("shopId") String shopId, Model m) {
        long id = Long.parseLong(shopId);
        ShopDto shop = shopService.findById(id);

        m.addAttribute("shop", shop);

        return "/user/shopInfo";
    }
}
