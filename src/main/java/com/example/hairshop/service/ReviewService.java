package com.example.hairshop.service;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Reservation;
import com.example.hairshop.domain.Review;
import com.example.hairshop.domain.User;
import com.example.hairshop.dto.ReviewForm;
import com.example.hairshop.repository.ReservationRepository;
import com.example.hairshop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void create(ReviewForm form) {
        long reservationId = Long.parseLong(form.getReservationId());
        Reservation reservation = reservationRepository.findOne(reservationId);
        User user = reservation.getUser();
        Designer designer = reservation.getDesigner();

        Review review = new Review();
        review.setRate(form.getRate());
        review.setContent(form.getContent());
        review.setUser(user);
        review.setDesigner(designer);
        review.setReservation(reservation);

        reviewRepository.save(review);

        reservation.setReview(review);
        user.getReviews().add(review);
        designer.getReviews().add(review);
    }
}
