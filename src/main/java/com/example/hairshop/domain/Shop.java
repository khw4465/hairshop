package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
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
    @OneToMany(mappedBy = "shop", cascade = ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    //== 샵 <--> 이미지 ==//
    @OneToMany(mappedBy = "shop", cascade = ALL, orphanRemoval = true)
    private List<ShopImg> shopImgs = new ArrayList<>();

    //== 샵 <--> 예약 ==//
    @OneToMany(mappedBy = "shop")
    private List<Reservation> reservations = new ArrayList<>();


    /**
     * 샵에 디자이너 등록하기
     */
    public void registDesigner(Designer designer) {
        this.designers.add(designer);
        designer.setShop(this);
    }
}
