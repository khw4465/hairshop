package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ReservationDetail {

    @Id @GeneratedValue
    @Column(name = "reservationDetailId")
    private Long id;

    //== 예약상세 <--> 예약 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    //== 예약상세 <--> 샵 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    //== 예약상세 <--> 시술메뉴 ==//
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "menuId")
    private Menu menu;

    //== 예약상세 <--> 디자이너 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "designerId")
    private Designer designer;

    //== 연관관계 메서드 ==//
    public void addReservation(Menu menu, Designer designer) {
        this.menu = menu;
        this.designer = designer;
        designer.getReservationDetails().add(this);
    }

    /**
     * 예약 주문하기
     */
//    public ReservationDetail setOrder(Designer designer, Menu menu) {
//        ReservationDetail reservation = new ReservationDetail(designer, menu);
//        designer.getSchedules().
//    }
}
