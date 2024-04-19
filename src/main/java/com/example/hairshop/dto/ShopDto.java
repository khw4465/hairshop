package com.example.hairshop.dto;

import com.example.hairshop.domain.Shop;
import com.example.hairshop.domain.ShopImg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {
    private Long id;
    private String name;
    private String shopCategory;
    private String address;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String content;
    private List<String> shopImgs;
    private List<DesignerDto> designers;
    private List<MenuDto> menus;
    private List<ReviewDto> reviews;

    public ShopDto(Long id, String name, String shopCategory, String address, List<String> shopImgs) {
        this.id = id;
        this.name = name;
        this.shopCategory = shopCategory;
        this.address = address;
        this.shopImgs = shopImgs;
    }

    public ShopDto(Long id, LocalTime openTime, LocalTime closeTime) {
        this.id = id;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public ShopDto(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.shopCategory = shop.getCategory().getName();
        this.address = shop.getAddress();
        this.openTime = shop.getOpenTime();
        this.closeTime = shop.getCloseTime();
        this.content = shop.getContent();
        this.shopImgs = shop.getShopImgs().stream().map(ShopImg::getImgUrl).toList();
        this.designers = shop.getDesigners().stream().map(DesignerDto::new).toList();
        this.menus = shop.getMenus().stream().map(MenuDto::new).toList();
        if (shop.getReviews() != null) {
            this.reviews = shop.getReviews().stream().map(ReviewDto::new).toList();
        }
    }
}
