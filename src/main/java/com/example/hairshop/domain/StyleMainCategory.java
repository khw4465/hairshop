package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class StyleMainCategory {

    @Id @GeneratedValue
    @Column(name = "styleMainCategoryId")
    private Long id;

    //== 메인 카테고리 이름 ==//
    private String name;

    //== 메인 카테고리 <--> 서브 카테고리 ==//
    @OneToMany(mappedBy = "mainCategory", orphanRemoval = true)
    private List<StyleSubCategory> categories = new ArrayList<>();

    public StyleMainCategory(String name) {
        this.name = name;
    }

    //== 연관관계 메서드 ==//
    public void addSubCategory(StyleSubCategory subCategory) {
        this.categories.add(subCategory);
        subCategory.setMainCategory(this);
    }
}
