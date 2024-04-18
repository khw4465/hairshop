package com.example.hairshop.repository;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Shop;
import com.example.hairshop.domain.ShopCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShopRepository {

    private final EntityManager em;

    /** 샵 생성 **/
    public void save(Shop shop) {
        em.persist(shop);
    }

    /** 샵 삭제 **/
    public void delete(Shop shop) {
        em.remove(shop);
    }

    /** 샵 조회(단건) **/
    public Shop findOne(Long id) {
        return em.find(Shop.class, id);
    }

    /** 샵 조회(전체) **/
    public List<Shop> findAll() {
        return em.createQuery("select s from Shop s", Shop.class)
                .getResultList();
    }

    /** 샵 조회(디자이너) **/
    public Shop findByDesigner(Designer designer) {
        return em.createQuery("select s from Shop s join fetch s.designers d where d = :designer", Shop.class)
                .setParameter("designer", designer)
                .getSingleResult();
    }

    /** 샵 조회(이름) **/
    public List<Shop> findByName(String name) {
        return em.createQuery("select s from Shop s where s.name like :name", Shop.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    /** 샵 조회(지역) **/
    public List<Shop> findByAddress(String address) {
        return em.createQuery("select s from Shop s where s.address like :address", Shop.class)
                .setParameter("name", "%" + address + "%")
                .getResultList();
    }

    /** 샵 조회(카테고리별) **/
    public List<Shop> findByCategory(String category) {
        return em.createQuery("select s from Shop s where s.category.name = :category", Shop.class)
                .setParameter("category", category)
                .getResultList();
    }

    /** 랜덤 페이징 전체 조회 **/
    public List<Shop> findRandomPageAll(int offset, int limit) {
        return em.createQuery("select s from Shop s order by FUNCTION('RAND')", Shop.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    //-------------------------------------------------------
    //페이징(어드민)
    /** 페이징 전체 조회 **/
    public List<Shop> findPageAll(int offset, int limit) {
        return em.createQuery("select s from Shop s", Shop.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    /** 전체 카운트쿼리 **/
    public Long countQueryAll() {
        return em.createQuery("select count(s) from Shop s", Long.class)
                .getSingleResult();
    }
    /** 페이징 이름 조회 **/
    public List<Shop> findPageByName(String name, int offset, int limit) {
        return em.createQuery("select s from Shop s where s.name like :name", Shop.class)
                .setParameter("name", "%" + name + "%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    /** 이름별 카운트쿼리 **/
    public long countQueryByName(String name) {
        return em.createQuery("select count(s) from Shop s where s.name like :name", Long.class)
                .setParameter("name", "%" + name + "%")
                .getSingleResult();
    }
    //-------------------------------------------------------
    //페이징(유저)
    /** 페이징 카테고리별 조히 **/
    public List<Shop> findPageByCategory(String name, int offset, int limit) {
        return em.createQuery("select s from Shop s where s.category.name = :name", Shop.class)
                .setParameter("name", name)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    /** 카테고리별 카운트 쿼리 **/
    public long countQueryByCategory(String name) {
        return em.createQuery("select count(s) from Shop s where s.category.name = :name", Long.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
