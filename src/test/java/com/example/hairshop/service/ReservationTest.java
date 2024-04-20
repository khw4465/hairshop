package com.example.hairshop.service;

import com.example.hairshop.domain.Reservation;
import com.example.hairshop.domain.Review;
import com.example.hairshop.domain.Status;
import com.example.hairshop.domain.User;
import com.example.hairshop.dto.ReservationDto;
import com.example.hairshop.repository.ReservationRepository;
import com.example.hairshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class ReservationTest {

    @Autowired ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 예약_확인() {
        ReservationDto byId = reservationService.findById("552");
        System.out.println("byId = " + byId);
    }

    @Test
    @Rollback(false)
    public void 예약_완료() {
        Reservation reservation = reservationRepository.findOne(552L);
        reservation.setStatus(Status.예약완료);

        ReservationDto byId = reservationService.findById("552");
        System.out.println("byId = " + byId);
    }

    @Test
    @Commit
    public void 예약_취소() {
        Reservation reservation = reservationRepository.findOne(552L);
        reservation.setStatus(Status.예약취소);

        ReservationDto byId = reservationService.findById("552");
        System.out.println("byId = " + byId);
    }

    @Test
    @Rollback(false)
    public void 시술_완료() {
        Reservation reservation = reservationRepository.findOne(52L);
        reservation.setStatus(Status.시술완료);

        ReservationDto byId = reservationService.findById("52");
        System.out.println("byId = " + byId);
    }

    @Test
    @Rollback(false)
    public void 리뷰조회() {
        Reservation reservation = reservationRepository.findOne(52L);
        Review review = reservation.getReview();
        System.out.println("review = " + review);
    }

    @Test
    public void 상태() {
//        List<Status> list = Arrays.asList(Status.values());
//        List<String> list1 = list.stream().map(Enum::name).toList();
//        for (String s : list1) {
//            System.out.println("s = " + s);
//        }

        Status status = Status.valueOf("예약완료");
        System.out.println("status = " + status);
    }
}
