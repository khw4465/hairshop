package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ReservationDetail {

    @Id @GeneratedValue
    @Column(name = "reservationDetailId")
    private Long id;

    //== 예약 요청사항 ==//
    private String content;

    //== 예약상세 <--> 예약 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    //== 예약상세 <--> 시술메뉴 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menuId")
    private Menu menu;

    //== 예약상세 <--> 스케쥴 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
}
