package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class Style extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "styleId")
    private Long id;

    //== 스타일 이미지 ==//
    private String imgUrl;

    //== 스타일 <--> 서브 카테고리 ==//
    @ManyToMany(mappedBy = "styles")
    private List<StyleSubCategory> subCategorys = new ArrayList<>();

    //== 스타일 <--> 디자이너 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "designerId")
    private Designer designer;

    public Style(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    //== 연관관계 메서드 ==//

    /**
     * 디자이너와 서브카테고리에 스타일 연관관계 맺기
     */
    public void addStyle(StyleSubCategory category, Designer designer) {
        category.getStyles().add(this);
        this.getSubCategorys().add(category);

        designer.getStyles().add(this);
        this.setDesigner(designer);
    }
}
