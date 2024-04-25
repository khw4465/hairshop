package com.example.hairshop.repository;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Shop;
import com.example.hairshop.domain.ShopCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShopRepositoryTest {

//    @Autowired ShopRepository shopRepository;
//    @Autowired ShopCategoryRepository shopCategoryRepository;
//    @Autowired DesignerRepository designerRepository;
//
//    @BeforeEach
//    public void before() {
//        //카테고리 생성
//        ShopCategory hairShop = new ShopCategory("HairShop");
//        ShopCategory nailShop = new ShopCategory("NailShop");
//        ShopCategory oneManShop = new ShopCategory("OneManShop");
//        ShopCategory barberShop = new ShopCategory("BarberShop");
//        shopCategoryRepository.save(hairShop);
//        shopCategoryRepository.save(nailShop);
//        shopCategoryRepository.save(oneManShop);
//        shopCategoryRepository.save(barberShop);
//    }
//
//    @Test
//    public void 샵_생성() {
//        Designer findDesigner = designerRepository.findOne(1L);
//        Shop shop = shopRepository.findByDesigner(findDesigner);
//        System.out.println("shop = " + shop.getName());
//    }
}