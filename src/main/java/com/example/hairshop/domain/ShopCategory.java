package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.example.hairshop.domain.MenuCategoryType.*;
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
    @Enumerated(EnumType.STRING)
    private ShopCategoryType name;

    //== 샵 카테고리 <--> 샵 ==//
    @OneToMany(mappedBy = "category")
    private List<Shop> shops = new ArrayList<>();

    //== 샵 카테고리 <--> 메뉴 카테고리 ==//
    @OneToMany(mappedBy = "category", cascade = ALL, orphanRemoval = true)
    private List<MenuCategory> categories = new ArrayList<>();

    public ShopCategory(ShopCategoryType name) {
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

    /**
     * 샵_카테고리에 메뉴 추가
     */
    public MenuCategory addMenuCategoryType(MenuCategoryType categoryType) {
        MenuCategory menuCategory;
        switch (this.name) {
            case NAIL_SHOP:
                if (categoryType == MANICURE || categoryType == PEDICURE) {
                    menuCategory = new MenuCategory(categoryType);
                    this.categories.add(menuCategory);
                    menuCategory.setCategory(this);
                    return menuCategory;
                } else {
                    throw new IllegalArgumentException("Invalid category type for NAIL_SHOP");
                }
            default:
                if (categoryType == CUT || categoryType == PERM || categoryType == DYE || categoryType == CLINIC || categoryType == STYLING) {
                    menuCategory = new MenuCategory(categoryType);
                    this.categories.add(menuCategory);
                    menuCategory.setCategory(this);
                    return menuCategory;
                } else {
                    throw new IllegalArgumentException("Invalid category type for " + this.name);
                }
        }
    }
}
