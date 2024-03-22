package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
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
    private LocalDateTime openTime;

    //== 마감시간 ==//
    private LocalDateTime closeTime;

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
    @OneToMany(mappedBy = "shop")
    private List<Menu> menus = new ArrayList<>();

    //== 샵 <--> 이미지 ==//
    @OneToMany(mappedBy = "shop")
    private List<ShopImg> shopImgs = new ArrayList<>();
}
