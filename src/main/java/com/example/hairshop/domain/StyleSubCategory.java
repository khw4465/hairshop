package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class StyleSubCategory {

    @Id @GeneratedValue
    @Column(name = "styleSubCategoryId")
    private Long id;

    //== 서브 카테고리 이름 ==//
    private String name;

    //== 서브 카테고리 <--> 메인 카테고리 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "styleMainCategoryId")
    private StyleMainCategory mainCategory;

    //== 서브 카테고리 <--> 스타일 ==//
    @ManyToMany(mappedBy = "subCategory")
    @JoinTable(name = "styleCategory",
            joinColumns = @JoinColumn(name = "styleSubCategoryId"),
            inverseJoinColumns = @JoinColumn(name = "styleId")
    )
    private List<Style> styles = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void addStyle(Style style) {
        style.getSubCategorys().add(this);
        this.styles.add(style);
    }
}
