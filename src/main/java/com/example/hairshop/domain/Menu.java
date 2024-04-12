package com.example.hairshop.domain;

import com.example.hairshop.dto.MenuDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

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

    //== 시술 금액 ==//
    private int price;

    //== 메뉴 <--> 메뉴 카테고리 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menuCategoryId")
    private MenuCategory category;

    //== 메뉴 <--> 샵 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    //== 메뉴 <--> 예약 상세 ==//
    @OneToOne(mappedBy = "menu", fetch = LAZY)
    private ReservationDetail reservationDetail;

    public Menu(String name, int price, MenuCategory category, Shop shop) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.shop = shop;
    }
}
