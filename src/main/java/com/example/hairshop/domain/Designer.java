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
@NoArgsConstructor(access = PROTECTED)
public class Designer extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "designerId")
    private Long id;

    //== 디자이너 이름 ==//
    private String name;

    //== 디자이너 사진 ==//
    private String img;

    //== 설명 ==//
    private String content;

    //== 디자이너 <--> 샵 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    //== 디자이너 <--> 경력 ==//
    @OneToMany(mappedBy = "designer")
    private List<Career> careers = new ArrayList<>();

    //== 디자이너 <--> 스타일 ==//
    @OneToMany(mappedBy = "designer")
    private List<Style> styles = new ArrayList<>();

    //== 디자이너 <--> 스케쥴 ==//
    @OneToMany(mappedBy = "designer")
    private List<Schedule> schedules = new ArrayList<>();

    //== 디자이너 <--> 리뷰 ==//
    @OneToMany(mappedBy = "designer")
    private List<Review> reviews = new ArrayList<>();
}
