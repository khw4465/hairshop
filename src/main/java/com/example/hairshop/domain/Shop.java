package com.example.hairshop.domain;

import com.example.hairshop.dto.MenuDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class Shop extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "shopId")
    private Long id;

    //== 샵 이름 ==//
    private String name;

    //== 샵 주소 ==//
    private String address;

    //== 오픈시간 ==//
    private LocalTime openTime;

    //== 마감시간 ==//
    private LocalTime closeTime;

    //== 샵 설명 ==//
    private String content;

    //== 샵 <--> 샵 카테고리 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shopCategoryId")
    private ShopCategory category;

    //== 샵 <--> 디자이너 ==//
    @OneToMany(mappedBy = "shop")
    private List<Designer> designers = new ArrayList<>();

    //== 샵 <--> 메뉴 ==//
    @OneToMany(mappedBy = "shop", cascade = ALL)
    private List<Menu> menus = new ArrayList<>();

    //== 샵 <--> 이미지 ==//
    @OneToMany(mappedBy = "shop", cascade = ALL)
    private List<ShopImg> shopImgs = new ArrayList<>();

    //== 샵 <--> 예약상세 ==//
    @OneToMany(mappedBy = "shop")
    private List<ReservationDetail> reservationDetails = new ArrayList<>();

    //== 연관관계 메서드 ==//
    /**
     * 샵 등록하기
     */
    public static Shop createShop(Designer designer, ShopCategory category, String name, String address, String content, LocalTime openTime, LocalTime closeTime, List<String> shopImgs, List<MenuDto> menuDtos) {
        Shop shop = new Shop(); //샵 객체생성
        shop.registDesigner(designer); //디자이너 등록
        category.addShopCategory(shop); //카테고리 등록
        shop.setName(name);
        shop.setAddress(address);
        shop.setOpenTime(openTime);
        shop.setCloseTime(closeTime);
        shop.setContent(content);

        List<ShopImg> shopImgList = ShopImg.createShopImgList(shopImgs); //이미지 리스트를 받아 샵 이미지 객체 리스트 생성
        shop.setShopImgs(shopImgList);
        for (ShopImg shopImg : shopImgList) { //연관관계 맺기
            shopImg.setShop(shop);
        }

        List<Menu> menus = Menu.createMenu(menuDtos); //메뉴DTO로 데이터를 받아 Menu로 변환
        shop.menus = menus;
        for (Menu menu : menus) {
            menu.setShop(shop);
        }

        return shop;
    }

    /**
     * 샵에 디자이너 등록하기
     */
    public void registDesigner(Designer designer) {
        this.designers.add(designer);
        designer.setShop(this);
    }
}
