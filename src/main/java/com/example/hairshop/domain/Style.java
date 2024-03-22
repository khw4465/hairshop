package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = PROTECTED)
public class Style extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "styleId")
    private Long id;

    //== 스타일 이미지 ==//
    private String imgUrl;

    //== 스타일 <--> 스타일 카테고리 ==//
    @ManyToMany(mappedBy = "styles")
    private List<StyleCategory> categories = new ArrayList<>();

    //== 스타일 <--> 디자이너 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "designerId")
    private Designer designer;
}
