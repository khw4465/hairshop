package com.example.hairshop.service;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.MenuDto;
import com.example.hairshop.dto.ShopDto;
import com.example.hairshop.repository.MenuCategoryRepository;
import com.example.hairshop.repository.ShopCategoryRepository;
import com.example.hairshop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopCategoryRepository shopCategoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    /** 샵 생성 **/
    @Transactional
    public void createShop(Designer designer, ShopDto shopDto) {
        Shop shop = new Shop();

        //카테고리 연관관계
        ShopCategory findCategory = shopCategoryRepository.findByName(shopDto.getShopCategory());
        shop.setCategory(findCategory);
        findCategory.getShops().add(shop);

        //메뉴 연관관계
        List<MenuDto> menus = shopDto.getMenus();
        for (MenuDto m : menus) {
            Menu menu = new Menu();

            //메뉴 <--> 메뉴카티고리 연관관계
            MenuCategory menuCategory = menuCategoryRepository.findByName(m.getCategory());
            menuCategory.getMenus().add(menu);
            menu.setCategory(menuCategory);

            menu.setName(m.getName());
            menu.setImgUrl(m.getImgUrl());
            menu.setPrice(m.getPrice());
            menu.setShop(shop);
            shop.getMenus().add(menu);
        }

        shop.setName(shopDto.getName());
        shop.setAddress(shopDto.getAddress());
        shop.setOpenTime(shopDto.getOpenTime());
        shop.setCloseTime(shopDto.getCloseTime());
        shop.setContent(shopDto.getContent());

        //디자이너 연관관계
        shop.getDesigners().add(designer);
        designer.setShop(shop);

        //샵 이미지 연관관계
        List<String> imgs = shopDto.getShopImgs();
        for (String img : imgs) {
            ShopImg shopImg = new ShopImg();
            shopImg.setImgUrl(img);
            shopImg.setShop(shop);
            shop.getShopImgs().add(shopImg);
        }

        shopRepository.save(shop);
    }
}
