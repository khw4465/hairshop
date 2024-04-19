package com.example.hairshop.service;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.ReviewDto;
import com.example.hairshop.dto.ReviewForm;
import com.example.hairshop.repository.ReservationRepository;
import com.example.hairshop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    /** 리뷰 생성 **/
    @Transactional
    public void create(ReviewForm form) {
        long reservationId = Long.parseLong(form.getReservationId());
        Reservation reservation = reservationRepository.findOne(reservationId);
        User user = reservation.getUser();
        Designer designer = reservation.getDesigner();
        Shop shop = reservation.getShop();

        Review review = new Review();
        review.setRate(form.getRate());
        review.setContent(form.getContent());
        review.setUser(user);
        review.setDesigner(designer);
        review.setShop(shop);
        review.setReservation(reservation);

        reviewRepository.save(review);

        reservation.setReview(review);
        user.getReviews().add(review);
        designer.getReviews().add(review);
        shop.getReviews().add(review);
    }

    /** 매장별 리뷰 조회 **/
    public List<ReviewDto> findByShopId(long id) {
        List<Review> reviewList = reviewRepository.findByShopId(id);
        List<ReviewDto> list = reviewList.stream().map(ReviewDto::new).toList();
        return list;
    }

    /** 매장 + 디자이너별 리뷰 조회 **/
    public List<ReviewDto> findByShopAndDesigner(long id1, long id2) {
        List<Review> reviews = reviewRepository.findByShopAndDesigner(id1, id2);
        List<ReviewDto> list = reviews.stream().map(ReviewDto::new).toList();
        return list;
    }
}
