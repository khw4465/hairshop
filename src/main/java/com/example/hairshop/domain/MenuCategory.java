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
public class MenuCategory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "menuCategoryId")
    private Long id;

    //== 카테고리 이름 ==//
    private String name;

    //== 메뉴 카테고리 <--> 메뉴 ==//
    @OneToMany(mappedBy = "category")
    private List<Menu> menus = new ArrayList<>();
}
