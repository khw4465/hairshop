package com.example.hairshop.controller;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.*;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class  AdminShopController {

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

    /** 샵 목록(페이징) **/
    @GetMapping("/admin/shop/list")
    public String shopList(@RequestParam(value = "offset", defaultValue = "0") int offset,
                           @RequestParam(value = "limit", defaultValue = "10") int limit,
                           Model m) {
        List<ShopDto> result = shopService.findPageAll(offset, limit);
        long count = shopService.countQueryAll();
        m.addAttribute("shopList", result);
        m.addAttribute("count", count);
        m.addAttribute("offset", offset);
        m.addAttribute("limit", limit);
        m.addAttribute("searchText", null);
        m.addAttribute("url", "/admin/shop/list");

        return "/admin/shopList";
    }

    /** 샵 검색(페이징) **/
    @GetMapping("/admin/search/shop")
    public String searchShop(@RequestParam("search") String search,
                                        @RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                                        Model m) {
        List<ShopDto> result = shopService.findPageByName(search, offset, limit);
        long count = shopService.countQueryByName(search);
            m.addAttribute("shopList", result);
            m.addAttribute("count", count);
            m.addAttribute("offset", offset);
            m.addAttribute("limit", limit);
            m.addAttribute("searchText", search);
            m.addAttribute("url", "/admin/search/shop");
        return "/admin/shopList";
    }

    /** 샵 삭제 **/
    @DeleteMapping("/admin/delete/shop")
    public ResponseEntity<?> deleteDesigner(@RequestParam("id") String id) {
        try {
            shopService.removeShop(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** 샵 상세 **/
    @GetMapping("/admin/shopInfo")
    public String shopInfo(@RequestParam("id") String id, Model m) {
        // 샵
        long shopId = Long.parseLong(id);
        ShopDto shop = shopService.findDtoById(shopId);
        m.addAttribute("shop", shop);

        // 샵 카테고리
        List<ShopCategory> shops = categoryService.findShopCategoryAll();
        List<ShopCategoryDto> shopCategoryList = shops.stream()
                .map(c -> new ShopCategoryDto(c.getName())).toList();
        m.addAttribute("shopCategories", shopCategoryList);

        // 메뉴 카테고리
        List<MenuCategory> menus = categoryService.findMenuCategoryAll();
        List<MenuCategoryDto> menuCategoryList = menus.stream()
                .map(c -> new MenuCategoryDto(c.getId(), c.getName())).toList();
        m.addAttribute("menuCategories", menuCategoryList);

        return "/admin/shopInfo";
    }

    /** 샵 정보 수정 **/
    @PutMapping("/admin/modify/shop")
    public ResponseEntity<?> modifyShop(@RequestBody ShopDto dto) {
        try {
            shopService.modifyShop(dto);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
