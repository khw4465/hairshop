package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class StyleCategory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "styleCategoryId")
    private Long id;

    //== 카테고리 이름 ==//
    private String name;

    //== 스타일 카테고리 <--> 스타일 ==//
    @ManyToMany
    @JoinTable(name = "StyleCategory_Style",
            joinColumns = @JoinColumn(name = "styleCategoryId"),
            inverseJoinColumns = @JoinColumn(name = "styleId")
    )
    private List<Style> styles = new ArrayList<>();

    //== 스타일 카테고리 <--> 부모 카테고리 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parentId")
    private StyleCategory parent;

    //== 스타일 카테고리 <--> 자식 카테고리 ==//
    @OneToMany(mappedBy = "parent")
    private List<StyleCategory> child = new ArrayList<>();
}
