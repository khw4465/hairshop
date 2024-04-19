package com.example.hairshop.repository;

import com.example.hairshop.domain.Review;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    /** 리뷰 생성 **/
    public void save(Review review) {
        em.persist(review);
    }
}
