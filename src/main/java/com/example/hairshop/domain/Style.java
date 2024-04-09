package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "Style{" +
                "id=" + id +
                ", imgUrl='" + imgUrl + '\'' +
                ", subCategorys=" + subCategorys +
                '}';
    }

    public Style(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
