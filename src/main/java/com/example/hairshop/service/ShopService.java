package com.example.hairshop.service;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.MenuDto;
import com.example.hairshop.dto.ShopDto;
import com.example.hairshop.repository.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopCategoryRepository shopCategoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;
    private final ShopImgRepository shopImgRepository;

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

    /** 샵 수정 **/
    @Transactional
    public Shop modifyShop(Designer designer, ShopDto shopDto) {
        Shop findShop = shopRepository.findByDesigner(designer);

        // 메뉴 <--> 카테고리 연관관계 제거
        List<Menu> originMenu = findShop.getMenus();
        for (Menu menu : originMenu) {
            menu.getCategory().getMenus().remove(menu);
            menu.setCategory(null);
            menu.setShop(null);
            menuRepository.delete(menu);
        }

        //메뉴 연관관계 수정
        List<MenuDto> menus = shopDto.getMenus();
        for (MenuDto dto : menus) {
            MenuCategory category = menuCategoryRepository.findByName(dto.getCategory());
            Menu menu = new Menu(dto.getName(), dto.getImgUrl(), dto.getPrice(), category, findShop);
            menuRepository.save(menu);
            category.getMenus().add(menu);
        }

        // 샵 <--> 카테고리 연관관계 제거
        ShopCategory category = findShop.getCategory();
        category.getShops().remove(findShop);

        //카테고리 연관관계 수정
        ShopCategory findCategory = shopCategoryRepository.findByName(shopDto.getShopCategory());
        findShop.setCategory(findCategory);
        findCategory.getShops().add(findShop);

        //기본 속성 수정
        findShop.setName(shopDto.getName());
        findShop.setAddress(shopDto.getAddress());
        findShop.setOpenTime(shopDto.getOpenTime());
        findShop.setCloseTime(shopDto.getCloseTime());
        findShop.setContent(shopDto.getContent());

        //샵 이미지 연관관계 제거
        List<ShopImg> originImgs = findShop.getShopImgs();
        findShop.getShopImgs().clear();
        for (ShopImg originImg : originImgs) {
            originImg.setShop(null);
            shopImgRepository.delete(originImg);
        }

        //샵 이미지 수정
        List<String> imgs = shopDto.getShopImgs();
        for (String img : imgs) {
            ShopImg shopImg = new ShopImg(img, findShop);
            shopImgRepository.save(shopImg);
            findShop.getShopImgs().add(shopImg);
        }

        return findShop;
    }

    /** 샵 삭제 **/
    @Transactional
    public void removeShop(Designer designer) {
        Shop findShop = shopRepository.findByDesigner(designer);

        for (Designer findShopDesigner : findShop.getDesigners()) {
            designer.setShop(null);
        }

        findShop.getCategory().getShops().remove(findShop);
        findShop.setCategory(null);

        for (Menu menu : findShop.getMenus()) {
            menu.getCategory().getMenus().remove(menu);
        }
        findShop.getMenus().clear();

        findShop.getShopImgs().clear();

        shopRepository.delete(findShop);
    }

    /** 디자이너로 찾기 **/
    public Shop findByDesigner(Designer designer) {
        return shopRepository.findByDesigner(designer);
    }

}
