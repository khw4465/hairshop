package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class ShopCategory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "shopCategoryId")
    private Long id;

    //== 카테고리 이름 ==//
    private String name;

    //== 샵 카테고리 <--> 샵 ==//
    @OneToMany(mappedBy = "category")
    private List<Shop> shops = new ArrayList<>();

    public ShopCategory(String name) {
        this.name = name;
    }

    //== 연관관계 메서드 ==//

    /**
     * 샵_카테고리에 샵 추가
     */
    public void addShopCategory(Shop shop) {
        this.shops.add(shop);
        shop.setCategory(this);
    }
}
