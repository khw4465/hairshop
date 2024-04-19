package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reservationId")
    private Long id;

    //== 예약 요청사항 ==//
    private String content;

    //== 예약 일시 ==//
    private LocalDateTime dateTime;

    //== 금액 ==//
    private int price;

    //== 예약 상태 ==//
    @Enumerated(EnumType.STRING)
    private Status status;

    //== 예약 <--> 회원 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

    //== 예약 <--> 디자이너 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "designerId")
    private Designer designer;

    //== 예약 <--> 샵 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    //== 예약 <--> 메뉴 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menuId")
    private Menu menu;

    //== 예약 <--> 리뷰 ==//
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

//    //== 예약 <--> 예약 상세 ==//
//    @OneToMany(mappedBy = "reservation")
//    private List<ReservationDetail> reservationDetails = new ArrayList<>();
//
//    /** 연관관계 메서드 **/
//    public void setReservation(User user) {
//        ReservationDetail reservationDetail = new ReservationDetail();
//
//    }
}
