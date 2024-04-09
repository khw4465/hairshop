package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class Schedule {

    @Id @GeneratedValue
    @Column(name = "scheduleId")
    private Long id;

    //== 예약일자 ==//
    private LocalDate date;

    //== 0  - 10:00  1  - 10:30  2  - 11:00  3  - 11:30 ==//
    //== 4  - 12:00  5  - 12:30  6  - 13:00  7  - 13:30 ==//
    //== 8  - 14:00  9  - 14:30  10 - 15:00  11 - 15:30 ==//
    //== 12 - 16:00  13 - 16:30  14 - 17:00  15 - 17:30 ==//
    //== 16 - 18:00  17 - 18:30  18 - 19:00  19 - 19:30 ==//
    private boolean[] time;

    //== 스케쥴 <--> 디자이너 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "designerId")
    private Designer designer;

    //== 특정 날짜의 모든 시간대 false로 초기화 ==//
    protected Schedule(LocalDate date) {
        this.date = date;
        this.time = new boolean[20];
        Arrays.fill(time, false);
    }

    /**
     * 선택된 시간대 true로 변경 후 반환
     */
    public Schedule getReserve(int index) {
        if (index >= 0 && index < 20) {
            this.time[index] = true;
            return this;
        } else {
            // 잘못 선택한 경우 예외처리
            throw new IllegalArgumentException("예약 가능한 시간대가 아닙니다.");
        }
    }
}
