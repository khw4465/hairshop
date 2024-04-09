package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reservationId")
    private Long id;

    //== 예약 요청사항 ==//
    private String content;

    //== 예약 상태 ==//
    private Status status;

    //== 예약 <--> 회원 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

    //== 예약 <--> 예약 상세 ==//
    @OneToMany(mappedBy = "reservation")
    private List<ReservationDetail> reservationDetails = new ArrayList<>();

    /** 연관관계 메서드 **/
    public void setReservation(User user) {
        ReservationDetail reservationDetail = new ReservationDetail();

    }
}
