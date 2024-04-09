package com.example.hairshop.controller;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.MenuCategory;
import com.example.hairshop.domain.ShopCategory;
import com.example.hairshop.dto.MenuCategoryDto;
import com.example.hairshop.dto.ShopCategoryDto;
import com.example.hairshop.dto.ShopDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.ShopService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final CategoryService categoryService;

    /** 샵 생성 화면 **/
    @GetMapping("/admin/createShop")
    public String shop(Model m) {
        // 샵 카테고리
        List<ShopCategory> shops = categoryService.findShopCategoryAll();
        List<ShopCategoryDto> shopList = shops.stream()
                .map(c -> new ShopCategoryDto(c.getName())).toList();
        m.addAttribute("shopCategories", shopList);

        // 메뉴 카테고리
        List<MenuCategory> menus = categoryService.findMenuCategoryAll();
        List<MenuCategoryDto> menuList = menus.stream()
                .map(c -> new MenuCategoryDto(c.getId(), c.getName())).toList();
        m.addAttribute("menuCategories", menuList);

        return "/admin/createShop";
    }

    /** 샵 생성 **/
    @PostMapping("/admin/create/shop")
    public ResponseEntity<?> createShop(@RequestBody ShopDto shopDto) {
        try{
            shopService.createShop(shopDto);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
