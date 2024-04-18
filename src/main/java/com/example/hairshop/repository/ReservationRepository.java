package com.example.hairshop.repository;

import com.example.hairshop.domain.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    /** 예약 생성 **/
    public void save(Reservation reservation) {
        em.persist(reservation);
    }

    /** 예약 조회(단건) **/
    public Reservation findOne(Long id) {
        return em.find(Reservation.class, id);
    }
    /** 예약 조회(전체) **/
    public List<Reservation> findAll() {
        return em.createQuery("select r from Reservation r", Reservation.class)
                .getResultList();
    }

    /** 예약 조회(특정 회원) **/
    public List<Reservation> findByUserId(Long userId, int offset, int limit) {
        return em.createQuery("select r from Reservation r where r.user.id = :userId", Reservation.class)
                .setParameter("userId", userId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /** 카운트 쿼리 **/
    public Long countQueryByUserId(Long userId) {
        return em.createQuery("select count(r) from Reservation r where r.user.id = :userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    /** 매장 아이디로 조회 (예약중인것만) **/
    public List<Reservation> findByShopId(Long id, Status status) {
        return em.createQuery("select r from Reservation r where r.shop.id = :shopId " +
                        "and r.status = :status", Reservation.class)
                .setParameter("shopId", id)
                .setParameter("status", status)
                .getResultList();
    }

    /** 매장 아이디 & 디자이너 아이디로 조회 (예약중인것만) **/
    public List<Reservation> findByShopAndDesigner(long id1, long id2, Status status) {
        return em.createQuery("select r from Reservation r where r.shop.id = :shopId " +
                        "and r.designer.id = :designerId " +
                        "and r.status = :status", Reservation.class)
                .setParameter("shopId", id1)
                .setParameter("designerId", id2)
                .setParameter("status", status)
                .getResultList();
    }

//    /** 예약 조회(디자이너별) **/
//    public List<Reservation> findByDesigner(Designer designer) {
//        return em.createQuery("select rd from ReservationDetail rd" +
//                        " where rd.designer = :designer" +
//                        " order by rd.schedule.date", Reservation.class)
//                .setParameter("designer", designer)
//                .getResultList();
//    }
//    /** 예약 조회(매장별) **/
//    public List<Reservation> findByShop(Shop shop) {
//        return em.createQuery("select rd from ReservationDetail rd" +
//                        " where rd.shop = :shop" +
//                        " order by rd.schedule.date", Reservation.class)
//                .setParameter("shop", shop)
//                .getResultList();
//    }
}
