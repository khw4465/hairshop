package com.example.hairshop.controller;

import com.example.hairshop.domain.MenuCategory;
import com.example.hairshop.domain.ShopCategory;
import com.example.hairshop.domain.StyleMainCategory;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitCategory implements CommandLineRunner {

    @Autowired
    CategoryService categoryService;
    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        //카테고리 생성
//        ShopCategory hairShop = new ShopCategory("헤어샵");
//        ShopCategory nailShop = new ShopCategory("네일샵");
//        ShopCategory oneManShop = new ShopCategory("1인샵");
//        ShopCategory barberShop = new ShopCategory("바버샵");
//        categoryService.saveShopCategory(hairShop);
//        categoryService.saveShopCategory(nailShop);
//        categoryService.saveShopCategory(oneManShop);
//        categoryService.saveShopCategory(barberShop);
//
//        MenuCategory menu1 = new MenuCategory("커트");
//        MenuCategory menu2 = new MenuCategory("펌");
//        MenuCategory menu3 = new MenuCategory("염색");
//        MenuCategory menu4 = new MenuCategory("클리닉");
//        MenuCategory menu5 = new MenuCategory("스타일링");
//        MenuCategory menu6 = new MenuCategory("네일");
//        MenuCategory menu7 = new MenuCategory("페디");
//        categoryService.saveMenuCategory(menu1);
//        categoryService.saveMenuCategory(menu2);
//        categoryService.saveMenuCategory(menu3);
//        categoryService.saveMenuCategory(menu4);
//        categoryService.saveMenuCategory(menu5);
//        categoryService.saveMenuCategory(menu6);
//        categoryService.saveMenuCategory(menu7);
//
//        StyleMainCategory mainCategory1 = new StyleMainCategory("커트");
//        StyleMainCategory mainCategory2 = new StyleMainCategory("펌");
//        StyleMainCategory mainCategory3 = new StyleMainCategory("염색");
//        StyleMainCategory mainCategory4 = new StyleMainCategory("네일");
//        StyleMainCategory mainCategory5 = new StyleMainCategory("페디");
//
//        categoryService.saveStyleMainCategory(mainCategory1);
//        categoryService.saveStyleMainCategory(mainCategory2);
//        categoryService.saveStyleMainCategory(mainCategory3);
//        categoryService.saveStyleMainCategory(mainCategory4);
//        categoryService.saveStyleMainCategory(mainCategory5);
//
//        StyleSubCategory cutCategory1 = new StyleSubCategory("레이어드컷");
//        StyleSubCategory cutCategory2 = new StyleSubCategory("허쉬컷");
//        StyleSubCategory cutCategory3 = new StyleSubCategory("픽시컷");
//        StyleSubCategory cutCategory4 = new StyleSubCategory("포마드컷");
//        StyleSubCategory cutCategory5 = new StyleSubCategory("투블럭컷");
//        StyleSubCategory cutCategory6 = new StyleSubCategory("댄디컷");
//        categoryService.saveStyleSubCateogry(cutCategory1);
//        categoryService.saveStyleSubCateogry(cutCategory2);
//        categoryService.saveStyleSubCateogry(cutCategory3);
//        categoryService.saveStyleSubCateogry(cutCategory4);
//        categoryService.saveStyleSubCateogry(cutCategory5);
//        categoryService.saveStyleSubCateogry(cutCategory6);
//
//        StyleMainCategory cut = categoryService.findMainCategoryByName("커트");
//        cut.addSubCategory(cutCategory1);
//        cut.addSubCategory(cutCategory2);
//        cut.addSubCategory(cutCategory3);
//        cut.addSubCategory(cutCategory4);
//        cut.addSubCategory(cutCategory5);
//        cut.addSubCategory(cutCategory6);
//
//        StyleSubCategory permCategory1 = new StyleSubCategory("히피펌");
//        StyleSubCategory permCategory2 = new StyleSubCategory("레이어드펌");
//        StyleSubCategory permCategory3 = new StyleSubCategory("네츄럴펌");
//        StyleSubCategory permCategory4 = new StyleSubCategory("S컬펌");
//        StyleSubCategory permCategory5 = new StyleSubCategory("애즈펌");
//        StyleSubCategory permCategory6 = new StyleSubCategory("가르마펌");
//        StyleSubCategory permCategory7 = new StyleSubCategory("쉐도우펌");
//        StyleSubCategory permCategory8 = new StyleSubCategory("베이비펌");
//        StyleSubCategory permCategory9 = new StyleSubCategory("스왈로펌");
//        StyleSubCategory permCategory10 = new StyleSubCategory("리젠트펌");
//        categoryService.saveStyleSubCateogry(permCategory1);
//        categoryService.saveStyleSubCateogry(permCategory2);
//        categoryService.saveStyleSubCateogry(permCategory3);
//        categoryService.saveStyleSubCateogry(permCategory4);
//        categoryService.saveStyleSubCateogry(permCategory5);
//        categoryService.saveStyleSubCateogry(permCategory6);
//        categoryService.saveStyleSubCateogry(permCategory7);
//        categoryService.saveStyleSubCateogry(permCategory8);
//        categoryService.saveStyleSubCateogry(permCategory9);
//        categoryService.saveStyleSubCateogry(permCategory10);
//
//        StyleMainCategory perm = categoryService.findMainCategoryByName("펌");
//        perm.addSubCategory(permCategory1);
//        perm.addSubCategory(permCategory2);
//        perm.addSubCategory(permCategory3);
//        perm.addSubCategory(permCategory4);
//        perm.addSubCategory(permCategory5);
//        perm.addSubCategory(permCategory6);
//        perm.addSubCategory(permCategory7);
//        perm.addSubCategory(permCategory8);
//        perm.addSubCategory(permCategory9);
//        perm.addSubCategory(permCategory10);
//
//        StyleSubCategory dyeCategory1 = new StyleSubCategory("다크브라운");
//        StyleSubCategory dyeCategory2 = new StyleSubCategory("카키브라운");
//        StyleSubCategory dyeCategory3 = new StyleSubCategory("블루블랙");
//        StyleSubCategory dyeCategory4 = new StyleSubCategory("애쉬그레이");
//        StyleSubCategory dyeCategory5 = new StyleSubCategory("애쉬카키");
//        StyleSubCategory dyeCategory6 = new StyleSubCategory("애쉬블루");
//        categoryService.saveStyleSubCateogry(dyeCategory1);
//        categoryService.saveStyleSubCateogry(dyeCategory2);
//        categoryService.saveStyleSubCateogry(dyeCategory3);
//        categoryService.saveStyleSubCateogry(dyeCategory4);
//        categoryService.saveStyleSubCateogry(dyeCategory5);
//        categoryService.saveStyleSubCateogry(dyeCategory6);
//
//        StyleMainCategory dye = categoryService.findMainCategoryByName("염색");
//        dye.addSubCategory(dyeCategory1);
//        dye.addSubCategory(dyeCategory2);
//        dye.addSubCategory(dyeCategory3);
//        dye.addSubCategory(dyeCategory4);
//        dye.addSubCategory(dyeCategory5);
//        dye.addSubCategory(dyeCategory6);
//
//        StyleSubCategory nailCategory1 = new StyleSubCategory("[매니큐어] 풀컬러");
//        StyleSubCategory nailCategory2 = new StyleSubCategory("[매니큐어] 그라데이션");
//        StyleSubCategory nailCategory3 = new StyleSubCategory("[매니큐어] 체크");
//        StyleSubCategory nailCategory4 = new StyleSubCategory("[매니큐어] 드로잉");
//        StyleSubCategory nailCategory5 = new StyleSubCategory("[매니큐어] 진주");
//        StyleSubCategory nailCategory6 = new StyleSubCategory("[매니큐어] 레터링");
//        categoryService.saveStyleSubCateogry(nailCategory1);
//        categoryService.saveStyleSubCateogry(nailCategory2);
//        categoryService.saveStyleSubCateogry(nailCategory3);
//        categoryService.saveStyleSubCateogry(nailCategory4);
//        categoryService.saveStyleSubCateogry(nailCategory5);
//        categoryService.saveStyleSubCateogry(nailCategory6);
//
//        StyleMainCategory nail = categoryService.findMainCategoryByName("네일");
//        nail.addSubCategory(nailCategory1);
//        nail.addSubCategory(nailCategory2);
//        nail.addSubCategory(nailCategory3);
//        nail.addSubCategory(nailCategory4);
//        nail.addSubCategory(nailCategory5);
//        nail.addSubCategory(nailCategory6);
//
//        StyleSubCategory pediCategory1 = new StyleSubCategory("[페디큐어] 풀컬러");
//        StyleSubCategory pediCategory2 = new StyleSubCategory("[페디큐어] 그라데이션");
//        StyleSubCategory pediCategory3 = new StyleSubCategory("[페디큐어] 체크");
//        StyleSubCategory pediCategory4 = new StyleSubCategory("[페디큐어] 드로잉");
//        StyleSubCategory pediCategory5 = new StyleSubCategory("[페디큐어] 진주");
//        StyleSubCategory pediCategory6 = new StyleSubCategory("[페디큐어] 레터링");
//        categoryService.saveStyleSubCateogry(pediCategory1);
//        categoryService.saveStyleSubCateogry(pediCategory2);
//        categoryService.saveStyleSubCateogry(pediCategory3);
//        categoryService.saveStyleSubCateogry(pediCategory4);
//        categoryService.saveStyleSubCateogry(pediCategory5);
//        categoryService.saveStyleSubCateogry(pediCategory6);
//
//        StyleMainCategory pedi = categoryService.findMainCategoryByName("페디");
//        pedi.addSubCategory(pediCategory1);
//        pedi.addSubCategory(pediCategory2);
//        pedi.addSubCategory(pediCategory3);
//        pedi.addSubCategory(pediCategory4);
//        pedi.addSubCategory(pediCategory5);
//        pedi.addSubCategory(pediCategory6);
    }
}
