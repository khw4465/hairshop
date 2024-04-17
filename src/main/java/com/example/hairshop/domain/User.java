package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "userId")
    private Long id;

    //== 카카오아이디  ==//
    private String kakaoId;

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

    //== 연관관계 메서드 ==//

    /**
     * 회원 생성
     */
    public static User createUser(String kakaoId, String name, String email) {
        User user = new User();
        user.setKakaoId(kakaoId);
        user.setName(name);
        user.setEmail(email);

        return user;
    }

    /**
     * 리뷰 작성하기
     */
//    public Review writeReview(double rate, String content) {
//        Review review = new Review(rate, content);
//    }
}
