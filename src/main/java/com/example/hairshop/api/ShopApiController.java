package com.example.hairshop.api;

import com.example.hairshop.domain.Shop;
import com.example.hairshop.dto.ShopDto;
import com.example.hairshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShopApiController {

    private final ShopService shopService;

    /** 샵 목록 **/
    @GetMapping("api/admin/shopList")
    public List<ShopDto> shopList() {
        List<Shop> all = shopService.findAll();

        List<ShopDto> list = all.stream()
                .map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(), s.getShopImgs().stream()
                        .map(i -> i.getImgUrl()).toList())).toList();

        return list;
    }

    @GetMapping("/api/user/shop/list")
    private List<ShopDto> shopCategoryList(@RequestParam("categoryName") String categoryName) {

        System.out.println("categoryName = " + categoryName);

        List<Shop> shop = shopService.findByCategory(categoryName);
        List<ShopDto> list = shop.stream()
                .map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(), s.getShopImgs().stream()
                        .map(i -> i.getImgUrl()).toList())).toList();

        return list;
    }
}
