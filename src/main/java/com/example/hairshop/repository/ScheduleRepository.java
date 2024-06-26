package com.example.hairshop.repository;

import com.example.hairshop.domain.Schedule;
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
public class ScheduleRepository {

    private final EntityManager em;

    /** 스케쥴 생성 **/
    public void create(Schedule schedule) {
        em.persist(schedule);
    }

    /** 스케쥴 삭제 **/
    public void remove(Schedule schedule) {
        em.remove(schedule);
    }

    /** 디자이너 스케쥴 객체 전체 조회 **/
    public List<Schedule> findAllScheduleByDesigner(Long designerId) {
        return em.createQuery("select s from Schedule s where s.designer.id = :designerId", Schedule.class)
                .setParameter("designerId", designerId)
                .getResultList();
    }

    /** 디자이너, 일자별 스케쥴 객체 조회 **/
    public Optional<Schedule> findSchedule(Long designerId, LocalDate date) {
        try {
            Schedule schedule = em.createQuery("select s from Schedule s join fetch s.designer d where d.id = :designerId and s.date =: date", Schedule.class)
                    .setParameter("designerId", designerId)
                    .setParameter("date", date)
                    .getSingleResult();
            return Optional.of(schedule);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /** 모든 스케쥴 **/
    public List<Schedule> findAll() {
        return em.createQuery("select s from Schedule s", Schedule.class)
                .getResultList();
    }
}
