package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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
}
