package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class MenuCategory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "menuCategoryId")
    private Long id;

    //== 카테고리 이름 ==//
    @Enumerated(EnumType.STRING)
    private MenuCategoryType name;

    //== 메뉴 카테고리 <--> 메뉴 ==//
    @OneToMany(mappedBy = "category")
    private List<Menu> menus = new ArrayList<>();

    //== 메뉴 카테고리 <--> 샵 카테고리 ==//
    @ManyToOne(fetch = LAZY)
    private ShopCategory category;

    public MenuCategory(MenuCategoryType category) {
        this.name = category;
    }

    //== 연관관계 메서드 ==//
    public void addMenuCategory(Menu menu) {
        this.menus.add(menu);
        menu.setCategory(this);
    }
}
