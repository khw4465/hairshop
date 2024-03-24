package com.example.hairshop.repository;

import com.example.hairshop.domain.MenuCategory;
import com.example.hairshop.domain.MenuCategoryType;
import com.example.hairshop.domain.ShopCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.hairshop.domain.MenuCategoryType.*;
import static com.example.hairshop.domain.ShopCategoryType.*;
import static com.example.hairshop.domain.ShopCategoryType.BARBER_SHOP;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CategoryTest {

    @Autowired
    ShopCategoryRepository shopCategoryRepository;

    @Autowired
    MenuCategoryRepository menuCategoryRepository;

    @BeforeEach
    public void before() {
        ShopCategory hairShop = new ShopCategory(HAIR_SHOP);
        ShopCategory nailShop = new ShopCategory(NAIL_SHOP);
        ShopCategory oneManShop = new ShopCategory(ONE_MAN_SHOP);
        ShopCategory barberShop = new ShopCategory(BARBER_SHOP);
        shopCategoryRepository.save(hairShop);
        shopCategoryRepository.save(nailShop);
        shopCategoryRepository.save(oneManShop);
        shopCategoryRepository.save(barberShop);
    }

    @Test
    @Rollback(false)
    public void 샵_메뉴_카테고리_추가_테스트() {
        ShopCategory findHairShop = shopCategoryRepository.findByName(HAIR_SHOP);
        ShopCategory findNailShop = shopCategoryRepository.findByName(NAIL_SHOP);
        ShopCategory findOneManShop = shopCategoryRepository.findByName(ONE_MAN_SHOP);
        ShopCategory findBarberShop = shopCategoryRepository.findByName(BARBER_SHOP);

        MenuCategory cut1 = findHairShop.addMenuCategoryType(CUT);
        MenuCategory perm1 = findHairShop.addMenuCategoryType(PERM);
        MenuCategory dye1 = findHairShop.addMenuCategoryType(DYE);
        MenuCategory clinic = findHairShop.addMenuCategoryType(CLINIC);
        MenuCategory styling = findHairShop.addMenuCategoryType(STYLING);

        menuCategoryRepository.save(cut1);
        menuCategoryRepository.save(perm1);
        menuCategoryRepository.save(dye1);
        menuCategoryRepository.save(clinic);
        menuCategoryRepository.save(styling);

        MenuCategory nail = findNailShop.addMenuCategoryType(MANICURE);
        MenuCategory pedi = findNailShop.addMenuCategoryType(PEDICURE);
        menuCategoryRepository.save(nail);
        menuCategoryRepository.save(pedi);

        MenuCategory cut2 = findOneManShop.addMenuCategoryType(CUT);
        MenuCategory perm2 = findOneManShop.addMenuCategoryType(PERM);
        MenuCategory dye2 = findOneManShop.addMenuCategoryType(DYE);
        menuCategoryRepository.save(cut2);
        menuCategoryRepository.save(perm2);
        menuCategoryRepository.save(dye2);

        MenuCategory cut3 = findBarberShop.addMenuCategoryType(CUT);
        MenuCategory perm3 = findBarberShop.addMenuCategoryType(PERM);
        MenuCategory dye3 = findBarberShop.addMenuCategoryType(DYE);
        menuCategoryRepository.save(cut3);
        menuCategoryRepository.save(perm3);
        menuCategoryRepository.save(dye3);

        assertThat(findHairShop.getCategories().size()).isEqualTo(5);
        assertThat(findNailShop.getCategories().size()).isEqualTo(2);
        assertThat(findOneManShop.getCategories().size()).isEqualTo(3);
        assertThat(findBarberShop.getCategories().size()).isEqualTo(3);
    }

    @Test
    public void 샵카테고리별_메뉴조회() {
        ShopCategory findHairShop = shopCategoryRepository.findByName(HAIR_SHOP);
        ShopCategory findNailShop = shopCategoryRepository.findByName(NAIL_SHOP);

        MenuCategory cut1 = findHairShop.addMenuCategoryType(CUT);
        MenuCategory perm1 = findHairShop.addMenuCategoryType(PERM);
        MenuCategory dye1 = findHairShop.addMenuCategoryType(DYE);
        MenuCategory clinic = findHairShop.addMenuCategoryType(CLINIC);
        MenuCategory styling = findHairShop.addMenuCategoryType(STYLING);

        menuCategoryRepository.save(cut1);
        menuCategoryRepository.save(perm1);
        menuCategoryRepository.save(dye1);
        menuCategoryRepository.save(clinic);
        menuCategoryRepository.save(styling);

        MenuCategory nail = findNailShop.addMenuCategoryType(MANICURE);
        MenuCategory pedi = findNailShop.addMenuCategoryType(PEDICURE);
        menuCategoryRepository.save(nail);
        menuCategoryRepository.save(pedi);

        List<MenuCategory> result1 = menuCategoryRepository.findByShopCategory(NAIL_SHOP);

        for (MenuCategory menuCategory : result1) {
            System.out.println("menuCategory = " + menuCategory.getName());
        }

        List<MenuCategory> result2 = menuCategoryRepository.findByShopCategory(HAIR_SHOP);

        for (MenuCategory menuCategory : result2) {
            System.out.println("menuCategory = " + menuCategory.getName());
        }
    }
}
