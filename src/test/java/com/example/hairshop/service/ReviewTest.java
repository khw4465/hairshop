package com.example.hairshop.service;

import com.example.hairshop.domain.*;
import com.example.hairshop.repository.ReviewRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewTest {

//    @Autowired
//    ReviewRepository reviewRepository;
//
//    @Autowired
//    EntityManager em;
//
//    @Test
//    public void 리뷰삭제() {
//        Review review = reviewRepository.findById(2l);
//        User user = review.getUser();
//        Designer designer = review.getDesigner();
//        Shop shop = review.getShop();
//        Reservation reservation = review.getReservation();
//
//        user.getReviews().remove(review);
//        designer.getReviews().remove(review);
//        shop.getReviews().remove(review);
//        reservation.setReview(null);
//
//        em.remove(review);
//    }
}
