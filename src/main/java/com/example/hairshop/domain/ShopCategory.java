package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;


@Entity
@Getter
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
}
