package com.example.hairshop.repository;

import com.example.hairshop.domain.ShopCategory;
import com.example.hairshop.domain.StyleCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShopCategoryRepository {

    private final EntityManager em;

    /** 샵 카테고리 생성 **/
    public void save(ShopCategory category) {
        em.persist(category);
    }

    /** 샵 카테고리 조회(단건) **/
    public ShopCategory findOne(Long id) {
        return em.find(ShopCategory.class, id);
    }

    /** 샵 카테고리 조회(전체) **/
    public List<ShopCategory> findAll() {
        return em.createQuery("select sc from ShopCategory sc", ShopCategory.class)
                .getResultList();
    }

    /** 샵 카테고리 이름 조회 **/
    public ShopCategory findByName(String name) {
        return em.createQuery("select sc from ShopCategory sc where sc.name = :name", ShopCategory.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
