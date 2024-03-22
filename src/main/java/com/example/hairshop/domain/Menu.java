package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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

    //== 시술메뉴 설명 ==//
    private String content;

    //== 메뉴 <--> 메뉴 카테고리 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menuCategoryId")
    private MenuCategory category;

    //== 메뉴 <--> 샵 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    //== 메뉴 <--> 예약 상세 ==//
    @OneToMany(mappedBy = "menu")
    private List<ReservationDetail> reservationDetails = new ArrayList<>();
}
