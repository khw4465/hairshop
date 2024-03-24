package com.example.hairshop.repository;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Schedule;
import com.example.hairshop.domain.Shop;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DesignerRepository {

    private final EntityManager em;

    /** 디자이너 생성 **/
    public void save(Designer designer) {
        em.persist(designer);
    }

    /** 디자이너 조회(단건) **/
    public Designer findOne(Long id) {
        return em.find(Designer.class, id);
    }

    /** 디자이너 조회(전체) **/
    public List<Designer> findAll() {
        return em.createQuery("select d from Designer d", Designer.class)
                .getResultList();
    }

    /** 디자이너 조회(특정 샵 전체) **/
    public List<Designer> findByShop(Shop shop) {
        return em.createQuery("select d from Designer d where d.shop = :shop", Designer.class)
                .setParameter("shop", shop)
                .getResultList();
    }

    /** 날짜별 디자이너 스케쥴 조회 **/
    public Schedule findScheduleByDate(Designer designer, LocalDate date) {
        return em.createQuery("select sc from Schedule sc join fetch sc.designer d where d = :designer and sc.date = :date", Schedule.class)
                .setParameter("designer", designer)
                .setParameter("date", date)
                .getSingleResult();
    }
}
