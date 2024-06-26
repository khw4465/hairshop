package com.example.hairshop.controller;

import com.example.hairshop.dto.*;
import com.example.hairshop.service.ReviewService;
import com.example.hairshop.service.ShopService;
import com.example.hairshop.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserShopController {

    private final ShopService shopService;
    private final StyleService styleService;
    private final ReviewService reviewService;

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

        return "userShopList";
    }

    /** 샵 상세정보 **/
    @GetMapping("/shop/info")
    public String shopInfo(@RequestParam("shopId") String shopId, Model m) {
        long id = Long.parseLong(shopId);
        ShopDto shop = shopService.findDtoById(id);
        m.addAttribute("shop", shop);

        List<MenuDto> menus = shop.getMenus();
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (MenuDto menu : menus) {
            set.add(menu.getCategory());
        }
        List<String> menuCategoryList = new ArrayList<>(set);
        m.addAttribute("menuCategory", menuCategoryList);

        List<StyleDto> styles = styleService.findByShop(id);
        m.addAttribute("styles", styles);

        List<ReviewDto> reviews = reviewService.findByShopId(id);
        m.addAttribute("reviewList", reviews);

        double rate = 0.0;
        for (ReviewDto review : reviews) {
            rate += review.getRate();
        }
        double avgRate = rate / reviews.size();
        double floor = Math.floor(avgRate);
        m.addAttribute("avgRate", avgRate);
        m.addAttribute("floor", floor);
        m.addAttribute("size", reviews.size());
        return "userShopInfo";
    }

    /** 별점 높은 순 **/
    @GetMapping("/shopList/rate")
    public String shopListByRate(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", defaultValue = "9") int limit,
                                 Model m) {
        List<ShopDto> byRate = shopService.findByRate(offset, limit);
        Long count = shopService.countQueryByRate();

        m.addAttribute("shopList", byRate);
        m.addAttribute("count", count);
        m.addAttribute("offset", offset);
        m.addAttribute("limit", limit);

        return "userShopListByRate";
    }
}
