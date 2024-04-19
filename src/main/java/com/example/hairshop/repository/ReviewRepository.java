package com.example.hairshop.repository;

import com.example.hairshop.domain.Review;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    /** 리뷰 생성 **/
    public void save(Review review) {
        em.persist(review);
    }

    /** 리뷰 조회(단건) **/
    public Review findById(Long id) {
        return em.find(Review.class, id);
    }

    /** 매장별 리뷰 **/
    public List<Review> findByShopId(long id) {
        return em.createQuery("select r from Review r join fetch r.shop s where s.id = :shopId", Review.class)
                .setParameter("shopId", id)
                .getResultList();
    }

    /** 매장 + 디자이너별 리뷰 조회 **/
    public List<Review> findByShopAndDesigner(long id1, long id2) {
        return em.createQuery("select r from Review r where r.shop.id = :shopId " +
                        "and r.designer.id = :designerId", Review.class)
                .setParameter("shopId", id1)
                .setParameter("designerId", id2)
                .getResultList();
    }
}
