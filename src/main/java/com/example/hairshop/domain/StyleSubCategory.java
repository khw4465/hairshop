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
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
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
    @ManyToMany
    @JoinTable(name = "styleCategory",
            joinColumns = @JoinColumn(name = "styleSubCategoryId"),
            inverseJoinColumns = @JoinColumn(name = "styleId")
    )
    private List<Style> styles = new ArrayList<>();

    public StyleSubCategory(String name) {
        this.name = name;
    }

}
