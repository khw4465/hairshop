package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reviewId")
    private Long id;

    //== 별점 ==//
    private double rate;

    //== 후기 ==//
    private String content;

    //== 리뷰 <--> 회원 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

    //== 리뷰 <--> 디자이너 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "designerId")
    private Designer designer;

    //== 리뷰 <--> 예약 ==//
    @OneToOne(fetch = LAZY, mappedBy = "review")
    private Reservation reservation;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rate=" + rate +
                ", content='" + content + '\'' +
                '}';
    }
}
