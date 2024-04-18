package com.example.hairshop.service;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.*;
import com.example.hairshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final DesignerRepository designerRepository;
    private final ShopCategoryRepository shopCategoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;
    private final ShopImgRepository shopImgRepository;

    /** 샵 생성 **/
    @Transactional
    public void createShop(ShopDto shopDto) {
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
            menu.setPrice(m.getPrice());
            menu.setShop(shop);
            shop.getMenus().add(menu);
        }

        shop.setName(shopDto.getName());
        shop.setAddress(shopDto.getAddress());
        shop.setOpenTime(shopDto.getOpenTime());
        shop.setCloseTime(shopDto.getCloseTime());
        shop.setContent(shopDto.getContent());

        //샵 이미지 연관관계
        List<String> imgs = shopDto.getShopImgs();
        for (String img : imgs) {
            ShopImg shopImg = new ShopImg();
            shopImg.setImgUrl(img);
            shopImg.setShop(shop);
            shop.getShopImgs().add(shopImg);
        }

        shopRepository.save(shop);

        //디자이너 연관관계
        List<DesignerDto> designers = shopDto.getDesigners();
        for (DesignerDto dto : designers) {
            Designer designer = designerRepository.findOne(dto.getId());
            shop.getDesigners().add(designer);
            designer.setShop(shop);
        }
    }

    /** 샵 수정 **/
    @Transactional
    public Shop modifyShop(ShopDto shopDto) {
        Shop originShop = shopRepository.findOne(shopDto.getId());

        // 디자이너 연관관계 제거
        for (Designer designer : originShop.getDesigners()) {
            designer.setShop(null);
        }
        originShop.getDesigners().clear();

        // 디자이너 연관관계 수정
        List<DesignerDto> designers = shopDto.getDesigners();
        for (DesignerDto dto : designers) {
            Designer designer = designerRepository.findOne(dto.getId());
            originShop.getDesigners().add(designer);
            designer.setShop(originShop);
        }

        // 메뉴 <--> 카테고리 연관관계 제거
        List<Menu> originMenu = originShop.getMenus();
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
            Menu menu = new Menu(dto.getName(), dto.getPrice(), category, originShop);
            menuRepository.save(menu);
            category.getMenus().add(menu);
        }

        // 샵 <--> 카테고리 연관관계 제거
        ShopCategory category = originShop.getCategory();
        category.getShops().remove(originShop);

        //카테고리 연관관계 수정
        ShopCategory findCategory = shopCategoryRepository.findByName(shopDto.getShopCategory());
        originShop.setCategory(findCategory);
        findCategory.getShops().add(originShop);

        //기본 속성 수정
        originShop.setName(shopDto.getName());
        originShop.setAddress(shopDto.getAddress());
        originShop.setOpenTime(shopDto.getOpenTime());
        originShop.setCloseTime(shopDto.getCloseTime());
        originShop.setContent(shopDto.getContent());

        //샵 이미지 연관관계 제거
        List<ShopImg> originImgs = originShop.getShopImgs();
        originShop.getShopImgs().clear();
        for (ShopImg originImg : originImgs) {
            originImg.setShop(null);
            shopImgRepository.delete(originImg);
        }

        //샵 이미지 수정
        List<String> imgs = shopDto.getShopImgs();
        for (String img : imgs) {
            ShopImg shopImg = new ShopImg(img, originShop);
            shopImgRepository.save(shopImg);
            originShop.getShopImgs().add(shopImg);
        }

        return originShop;
    }

    /** 샵 삭제 **/
    @Transactional
    public void removeShop(String id) {
        long shopId = Long.parseLong(id);
        Shop shop = shopRepository.findOne(shopId);

        // 디자이너 연관관계 제거
        for (Designer designer : shop.getDesigners()) {
            designer.setShop(null);
        }

        // 카테고리 연관관계 제거
        shop.getCategory().getShops().remove(shop);

        // 메뉴<-->카테고리 연관관계 제거
        for (Menu menu : shop.getMenus()) {
            menu.getCategory().getMenus().remove(menu);
        }

        shopRepository.delete(shop);
    }

    /** 디자이너로 찾기 **/
    public Shop findByDesigner(Designer designer) {
        return shopRepository.findByDesigner(designer);
    }

    /** 샵 단일 조회 **/
    public Shop findById(Long id) {
        return shopRepository.findOne(id);
    }

    /** 샵 단일 조회 (DTO) **/
    public ShopDto findDtoById(Long id) {
        Shop shop = shopRepository.findOne(id);
        //dto로 변환
        ShopDto dto = new ShopDto(shop.getId(), shop.getName(), shop.getCategory().getName(), shop.getAddress(), shop.getOpenTime(), shop.getCloseTime(), shop.getContent(),
                shop.getShopImgs().stream().map(ShopImg::getImgUrl).toList(),
                shop.getDesigners().stream().map(sd -> new DesignerDto(sd.getId(), sd.getName(), sd.getImg(), sd.getContent(), sd.getCareer(),
                        sd.getStyles().stream().map(StyleDto::new).toList(),
                        sd.getReviews().stream().map(ReviewDto::new).toList())).toList(),
                shop.getMenus().stream().map(MenuDto::new).toList());

        return dto;
    }

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    /** 모든 샵 조회 (모든 데이터) **/
    public List<ShopDto> findAllShopData() {
        List<Shop> all = shopRepository.findAll();
        List<ShopDto> list = all.stream().map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(), s.getOpenTime(), s.getCloseTime(), s.getContent(),
                s.getShopImgs().stream().map(ShopImg::getImgUrl).toList(),
                s.getDesigners().stream().map(sd -> new DesignerDto(sd.getId(), sd.getName(), sd.getImg(), sd.getContent(), sd.getCareer(),
                        sd.getStyles().stream().map(StyleDto::new).toList(),
                        sd.getReviews().stream().map(ReviewDto::new).toList())).toList(),
                s.getMenus().stream().map(MenuDto::new).toList())).toList();
        return list;
    }

    /** 이름으로 검색 **/
    public List<Shop> findByName(String name) {
        return shopRepository.findByName(name);
    }

    /** 카테고리명으로 검색 **/
    public List<Shop> findByCategory(String name) {
        return shopRepository.findByCategory(name);
    }

    /** 샵 랜덤 페이징 전체 조회 **/
    public List<ShopDto> findRandomPageAll(int offset, int limit) {
        List<Shop> pageAll = shopRepository.findRandomPageAll(offset, limit);
        List<ShopDto> list = pageAll.stream()
                .map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(),
                        s.getShopImgs().stream().map(ShopImg::getImgUrl).toList())).toList();
        return list;
    }

    //-----------------------------------------------------------
    //페이징(어드민)
    /** 샵 페이징 전체 조회 **/
    public List<ShopDto> findPageAll(int offset, int limit) {
        List<Shop> pageAll = shopRepository.findPageAll(offset, limit);
        List<ShopDto> list = pageAll.stream()
                .map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(),
                        s.getShopImgs().stream().map(ShopImg::getImgUrl).toList())).toList();
        return list;
    }
    /** 전체 카운트 쿼리 **/
    public Long countQueryAll() {
        return shopRepository.countQueryAll();
    }
    /** 샵 페이징 이름 조회 **/
    public List<ShopDto> findPageByName(String name, int offset, int limit) {
        List<Shop> pageByName = shopRepository.findPageByName(name, offset, limit);
        List<ShopDto> list = pageByName.stream()
                .map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(),
                        s.getShopImgs().stream().map(si -> si.getImgUrl()).toList())).toList();
        return list;
    }
    /** 이름별 카운트 쿼리 **/
    public long countQueryByName(String name) {
        return shopRepository.countQueryByName(name);
    }

    //-----------------------------------------------------------
    //페이징(유저)
    /** 페이징 카테고리별 조회 **/
    public List<ShopDto> findPageByCategory(String name, int offset, int limit) {
        List<Shop> pageByCategory = shopRepository.findPageByCategory(name, offset, limit);
        List<ShopDto> list = pageByCategory.stream()
                .map(s -> new ShopDto(s.getId(), s.getName(), s.getCategory().getName(), s.getAddress(),
                        s.getShopImgs().stream().map(si -> si.getImgUrl()).toList())).toList();
        return list;
    }

    /** 카테고리별 카운트 쿼리 **/
    public long countQueryByCategory(String name) {
        return shopRepository.countQueryByCategory(name);
    }
}
