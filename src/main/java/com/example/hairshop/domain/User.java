package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "userId")
    private Long id;

    //== 회원이름 ==//
    private String name;

    //== 전화번호 ==//
    private String phoneNum;

    //== 이메일 ==//
    private String email;

    //== 성별 ==//
    @Enumerated(EnumType.STRING)
    private Gender gender;

    //== 회원 <--> 예약 ==//
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    //== 회원 <--> 리뷰 ==//
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();
}
