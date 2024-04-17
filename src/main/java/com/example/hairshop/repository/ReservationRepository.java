package com.example.hairshop.repository;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Reservation;
import com.example.hairshop.domain.Shop;
import com.example.hairshop.domain.User;
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
    public List<Reservation> findByUser(User user) {
        return em.createQuery("select r from Reservation r where r.user = :user", Reservation.class)
                .setParameter("user", user)
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
