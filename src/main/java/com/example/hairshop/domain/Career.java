package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class Career extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "careerId")
    private Long id;

    //== 경력란 ==//
    private String content;

    //== 디자이너 <--> 경력 ==//
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "designerId")
    private Designer designer;

    public Career(String content) {
        this.content = content;
    }
}
