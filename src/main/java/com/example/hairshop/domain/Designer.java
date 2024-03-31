package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class Designer extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "designerId")
    private Long id;

    //== 카카오아이디 ==//
    private String kakaoId;

    //== 디자이너 이름 ==//
    private String name;

    //== 디자이너 사진 ==//
    private String img;

    //== 설명 ==//
    private String content;

    //== 디자이너 <--> 경력 ==//
    @OneToOne(mappedBy = "designer", cascade = ALL, orphanRemoval = true)
    private Career career;

    //== 디자이너 <--> 샵 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    //== 디자이너 <--> 스타일 ==//
    @OneToMany(mappedBy = "designer", cascade = ALL, orphanRemoval = true)
    private List<Style> styles = new ArrayList<>();

    //== 디자이너 <--> 스케쥴 ==//
    @OneToMany(mappedBy = "designer", cascade = ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    //== 디자이너 <--> 리뷰 ==//
    @OneToMany(mappedBy = "designer")
    private List<Review> reviews = new ArrayList<>();

    //== 디자이너 <--> 예약상세 ==//
    @OneToMany(mappedBy = "designer")
    private List<ReservationDetail> reservationDetails = new ArrayList<>();

    //== 연관관계 메서드 ==//
    /**
     * 디자이너 생성
     */
    public static Designer createDesigner(String kakaoId, String name) {
        Designer designer = new Designer();
        designer.setKakaoId(kakaoId);
        designer.setName(name);
        designer.setImg("/img/basicProfile.png");
        designer.getReservationTime();
        return designer;
    }

    /**
     * 디자이너 수정
     */
    public static Designer addDesignerInfo(Designer designer, String name, String imgUrl, String content, String career) {
        designer.setName(name);
        designer.setImg(imgUrl);
        designer.setContent(content);
        designer.addCareer(career);

        return designer;
    }

    /**
     * 날짜별 스케쥴 생성
     */
    public void getReservationTime() {
        LocalDate currentDate = LocalDate.now();
        //현재 날짜로부터 3개월 후의 스케쥴을 생성하도록 설정
        for (int i = 0; i < 3; i++) {
            YearMonth yearMonth = YearMonth.from(currentDate.plusMonths(i));
            int lastDayOfMonth = yearMonth.lengthOfMonth(); // 해당 월의 마지막 날짜 구하기
            //새로 추가된 월의 첫째 날부터 마지막 날까지 스케쥴 생성
            LocalDate newMonthFirstDay = yearMonth.atDay(1);
            for (int day = 1; day <= lastDayOfMonth; day++) {
                LocalDate date = newMonthFirstDay.plusDays(day - 1);
                Schedule schedule = new Schedule(date);
                this.schedules.add(schedule);
                schedule.setDesigner(this);
            }
            // 매월 1일일 때 한 달을 추가하여 스케쥴 생성
            if (currentDate.getDayOfMonth() == 1) {
                currentDate = currentDate.plusMonths(1); // 한 달 추가
            }
        }
    }

    /**
     * 경력 생성
     */
    public void addCareer(String content) {
        Career career = new Career(content);
        career.setDesigner(this);
        this.setCareer(career);
    }
}
