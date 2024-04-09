package com.example.hairshop.controller;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.*;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.ShopService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /** 샵 목록 **/
    @GetMapping("/admin/shopList")
    public String shopList(Model m) {
        List<Shop> all = shopService.findAll();

        List<ShopDto> list = all.stream()
                .map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(), s.getShopImgs().stream()
                        .map(i -> i.getImgUrl()).toList())).toList();
        m.addAttribute("shopList", list);

        return "/admin/shopList";
    }

    /** 샵 검색 **/
    @PostMapping("/admin/search/shop")
    public ResponseEntity<?> searchShop(@RequestBody SearchCondition condition) {
        try {
            List<Shop> searchShop = shopService.findByName(condition.getName());
            List<ShopDto> shopList = searchShop.stream()
                    .map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(), s.getShopImgs().stream()
                            .map(i -> i.getImgUrl()).toList())).toList();

            return new ResponseEntity<>(shopList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
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
        long shopId = Long.parseLong(id);
        Shop shop = shopService.findById(shopId);

        // 샵
        ShopDto dto = new ShopDto(shop.getId(), shop.getName(), shop.getCategory().getName(), shop.getAddress(), shop.getOpenTime(), shop.getCloseTime(), shop.getContent(),
                shop.getShopImgs().stream().map(i -> i.getImgUrl()).toList(),
                shop.getDesigners().stream().map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList(),
                shop.getMenus().stream().map(e -> new MenuDto(e.getName(), e.getImgUrl(), e.getPrice(), e.getCategory().getName())).toList());
        m.addAttribute("shop", dto);

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
