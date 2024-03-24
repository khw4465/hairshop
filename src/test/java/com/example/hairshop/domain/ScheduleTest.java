package com.example.hairshop.domain;

import com.example.hairshop.dto.StyleDto;
import com.example.hairshop.repository.*;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.hairshop.domain.MenuCategoryType.*;
import static com.example.hairshop.domain.ShopCategoryType.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleTest {

    @Autowired
    DesignerRepository designerRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MenuCategoryRepository menuCategoryRepository;

    @Autowired
    StyleCategoryRepository styleCategoryRepository;

    @Autowired
    ShopCategoryRepository shopCategoryRepository;

    @Autowired
    EntityManager em;

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

        ShopCategory findHairShop = shopCategoryRepository.findOne(hairShop.getId());
        ShopCategory findNailShop = shopCategoryRepository.findOne(nailShop.getId());
        ShopCategory findOneManShop = shopCategoryRepository.findOne(oneManShop.getId());
        ShopCategory findBarberShop = shopCategoryRepository.findOne(barberShop.getId());

        MenuCategory cut1 = findHairShop.addMenuCategoryType(CUT);
        MenuCategory perm1 = findHairShop.addMenuCategoryType(PERM);
        MenuCategory dye1 = findHairShop.addMenuCategoryType(DYE);
        menuCategoryRepository.save(cut1);
        menuCategoryRepository.save(perm1);
        menuCategoryRepository.save(dye1);

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
    }

    @Test
    @Rollback(false)
    public void 스케쥴_확인() {
        ShopCategory category = new ShopCategory();
        category.setName(HAIR_SHOP);
        em.persist(category);

        Shop shop = new Shop();
        shop.setName("샵A");
        shop.setContent("안녕하세요");
        shopRepository.save(shop);

        category.addShopCategory(shop);

        List<Shop> shops = category.getShops();
        for (Shop shop1 : shops) {
            System.out.println("shop1 = " + shop1.getName());
        }

        Shop findShop = shopRepository.findOne(shop.getId());

        StyleCategory cut1 = new StyleCategory("아이비리그 컷");
        StyleCategory dye1 = new StyleCategory("애쉬 브라운");

        List<StyleCategory> categories1 = new ArrayList<>();
        categories1.add(cut1);
        categories1.add(dye1);

        StyleCategory cut2 = new StyleCategory("아이비리그 컷");
        StyleCategory dye2 = new StyleCategory("애쉬 브라운");

        List<StyleCategory> categories2 = new ArrayList<>();
        categories2.add(cut2);
        categories2.add(dye2);

        List<StyleDto> list = new ArrayList<>();
        list.add(new StyleDto("스타일A", categories1));
        list.add(new StyleDto("스타일B", categories2));

        Designer designer = Designer.createDesigner("디자이너A", "사진A", "안녕하세요", "-OO에서 근무", list);
        designer.setShop(findShop);
        designerRepository.save(designer);

        Designer findDesigner = designerRepository.findOne(designer.getId());
        findDesigner.getReservationTime();

        List<Schedule> schedules = findDesigner.getSchedules();
        for (Schedule schedule : schedules) {
            em.persist(schedule);
            System.out.println("schedule = " + schedule.getDate() + "," + schedule.getTime()[0]);
        }
    }

    @Test
    @Rollback(false)
    public void 날짜별_디자이너_스케줄조회() {
        StyleCategory styleA1 = new StyleCategory("아이비리그 컷");
        StyleCategory styleA2 = new StyleCategory("애쉬 브라운");

        List<StyleCategory> categories1 = new ArrayList<>();
        categories1.add(styleA1);
        categories1.add(styleA2);

        StyleCategory styleB1 = new StyleCategory("리프 컷");
        StyleCategory styleB2 = new StyleCategory("히피펌");

        List<StyleCategory> categories2 = new ArrayList<>();
        categories2.add(styleB1);
        categories2.add(styleB2);

        List<StyleDto> list = new ArrayList<>();
        list.add(new StyleDto("스타일A", categories1));
        list.add(new StyleDto("스타일B", categories2));

        Designer designer = Designer.createDesigner("디자이너A", "사진A", "안녕하세요", "XX에서 5년 근무", list);
        designerRepository.save(designer);

        Designer findDesigner = designerRepository.findOne(designer.getId());
        Schedule schedule = designerRepository.findScheduleByDate(findDesigner, LocalDate.of(2024, 4, 1));

        System.out.println("schedule = " + schedule.getDate());
    }
}