package com.example.hairshop.repository;

import com.example.hairshop.domain.MenuCategory;
import com.example.hairshop.domain.ShopCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuCategoryRepository {

    private final EntityManager em;

    /** 메뉴 카테고리 생성 **/
    public void save(MenuCategory category) {
        em.persist(category);
    }

    /** 메뉴 카테고리 조회(단건) **/
    public MenuCategory findOne(Long id) {
        return em.find(MenuCategory.class, id);
    }

    /** 메뉴 카테고리 조회(전체) **/
    public List<MenuCategory> findAll() {
        return em.createQuery("select mc from MenuCategory mc", MenuCategory.class)
                .getResultList();
    }
    public MenuCategory findByName(String name) {
         return em.createQuery("select mc from MenuCategory mc where mc.name = :name", MenuCategory.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
