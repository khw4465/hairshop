package com.example.hairshop.repository;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Schedule;
import com.example.hairshop.domain.Shop;
import com.example.hairshop.domain.Style;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DesignerRepository {

    private final EntityManager em;

    /** 디자이너 생성 **/
    public void save(Designer designer) {
        em.persist(designer);
    }

    /** 디자이너 삭제 **/
    public void delete(Designer designer) {
        em.remove(designer);
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

    /** 디자이너 이름 조회 **/
    public List<Designer> findByName(String name) {
        return em.createQuery("select d from Designer d where d.name like :name", Designer.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    /** 카카오아이디 디자이너 유무 조회 **/
    public Optional<Designer> findOptionalByKakao(String kakaoId) {
        try {
            Designer designer = em.createQuery("select d from Designer d where d.kakaoId = :kakaoId", Designer.class)
                    .setParameter("kakaoId", kakaoId)
                    .getSingleResult();
            return Optional.of(designer);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /** 카카오아이디 디자이너 정보 조회 **/
    public Designer findByKakao(String kakaoId) {
        return em.createQuery("select d from Designer d where d.kakaoId = :kakaoId", Designer.class)
                .setParameter("kakaoId", kakaoId)
                .getSingleResult();
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

    /** 디자이너의 스타일 조회 **/
    public List<Style> findStyle(Designer designer) {
        return em.createQuery("select s from Style s where s.designer = :designer", Style.class)
                .getResultList();
    }
}
