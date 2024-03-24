package com.example.hairshop.repository;

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

    /** 샵 조회(단건) **/
    public Shop findOne(Long id) {
        return em.find(Shop.class, id);
    }

    /** 샵 조회(전체) **/
    public List<Shop> findAll() {
        return em.createQuery("select s from Shop s", Shop.class)
                .getResultList();
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
    public List<Shop> findByCategory(ShopCategory category) {
        return em.createQuery("select s from Shop s where s.category = :category", Shop.class)
                .setParameter("category", category)
                .getResultList();
    }
}
