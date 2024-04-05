package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ShopImg extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "shopImgId")
    private Long id;

    //== 이미지 ==//
    private String imgUrl;

    //== 샵 이미지 <--> 샵 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    public ShopImg(String imgUrl, Shop shop) {
        this.imgUrl = imgUrl;
        this.shop = shop;
    }

    //== 연관관계 메서드 ==//

    /**
     * 샵 이미지 리스트 생성
     */
    public static List<ShopImg> createShopImgList(List<String> imgUrls) {
        List<ShopImg> imgList = new ArrayList<>();
        for (String imgUrl : imgUrls) {
            ShopImg shopImg = new ShopImg();
            shopImg.setImgUrl(imgUrl);
            imgList.add(shopImg);
        }
        return imgList;
    }
}
