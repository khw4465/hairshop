package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class StyleMainCategory {

    @Id @GeneratedValue
    @Column(name = "styleMainCategoryId")
    private Long id;

    //== 메인 카테고리 이름 ==//
    private String name;

    //== 메인 카테고리 <--> 서브 카테고리 ==//
    @OneToMany(mappedBy = "mainCategory")
    private List<StyleSubCategory> categories = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void addSubCategory(StyleSubCategory subCategory) {
        this.categories.add(subCategory);
        subCategory.setMainCategory(this);
    }
}
