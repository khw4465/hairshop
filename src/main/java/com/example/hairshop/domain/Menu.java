package com.example.hairshop.domain;

import com.example.hairshop.dto.MenuDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "menuId")
    private Long id;

    //== 시술메뉴 이름 ==//
    private String name;

    //== 시술 사진 ==//
    private String imgUrl;

    //== 시술 금액 ==//
    private int price;

    //== 메뉴 <--> 메뉴 카테고리 ==//
    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "menuCategoryId")
    private MenuCategory category;

    //== 메뉴 <--> 샵 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    //== 메뉴 <--> 예약 상세 ==//
    @OneToOne(mappedBy = "menu", fetch = LAZY)
    private ReservationDetail reservationDetail;

    //== 연관관계 메서드 ==//

    /**
     * 메뉴 객체 생성
     */
    public static List<Menu> createMenu(List<MenuDto> menuDtos) {
        List<Menu> menuList = new ArrayList<>();
        for (MenuDto menuDto : menuDtos) {
            Menu menu = new Menu();
            menu.setName(menuDto.getName());
            menu.setImgUrl(menuDto.getImgUrl());
            menu.setPrice(menuDto.getPrice());
//            menuDto.getCategory()(menu);
            menuList.add(menu);
        }
        return menuList;
    }
}
