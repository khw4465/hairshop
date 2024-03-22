package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Schedule {

    @Id @GeneratedValue
    @Column(name = "scheduleId")
    private Long id;

    //== 예약일자 ==//
    private LocalDateTime date;

    //== 10:00 ~ 10:30 ==//
    private boolean time1;
    //== 10:30 ~ 11:00 ==//
    private boolean time2;
    //== 11:00 ~ 11:30 ==//
    private boolean time3;
    //== 11:30 ~ 12:00 ==//
    private boolean time4;
    //== 12:00 ~ 12:30 ==//
    private boolean time5;
    //== 12:30 ~ 13:00 ==//
    private boolean time6;
    //== 13:00 ~ 13:30 ==//
    private boolean time7;
    //== 13:30 ~ 14:00 ==//
    private boolean time8;
    //== 14:00 ~ 14:30 ==//
    private boolean time9;
    //== 14:30 ~ 15:00 ==//
    private boolean time10;
    //== 15:00 ~ 15:30 ==//
    private boolean time11;
    //== 15:30 ~ 16:00 ==//
    private boolean time12;
    //== 16:00 ~ 16:30 ==//
    private boolean time13;
    //== 16:30 ~ 17:00 ==//
    private boolean time14;
    //== 17:00 ~ 17:30 ==//
    private boolean time15;
    //== 17:30 ~ 18:00 ==//
    private boolean time16;
    //== 18:00 ~ 18:30 ==//
    private boolean time17;
    //== 18:30 ~ 19:00 ==//
    private boolean time18;
    //== 19:00 ~ 19:30 ==//
    private boolean time19;
    //== 19:30 ~ 20:00 ==//
    private boolean time20;

    //== 스케쥴 <--> 디자이너 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "designerId")
    private Designer designer;

    //== 스케쥴 <--> 예약 상세 ==//
    @OneToMany(mappedBy = "schedule")
    private List<ReservationDetail> reservationDetails = new ArrayList<>();
}
