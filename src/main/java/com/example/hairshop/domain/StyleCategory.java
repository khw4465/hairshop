package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class StyleCategory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "styleCategoryId")
    private Long id;

    //== 카테고리 이름 ==//
    private String name;

    //== 스타일 카테고리 <--> 스타일 ==//
    @ManyToMany
    @JoinTable(name = "StyleCategoryStyle",
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

    public StyleCategory() {}

    public StyleCategory(String name) {
        this.name = name;
    }

    //==연관관계 메서드==//
    public void addChildCategory(StyleCategory child) {
        this.child.add(child);
        child.setParent(this);
    }
    public void addStyleCategory(Style style) {
        this.styles.add(style);
        style.setCategories(child);
    }
}
