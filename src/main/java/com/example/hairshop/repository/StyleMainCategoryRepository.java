package com.example.hairshop.repository;

import com.example.hairshop.domain.StyleMainCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class StyleMainCategoryRepository {

    private final EntityManager em;

    /** 메인 카테고리 생성 **/
    public void save(StyleMainCategory category) {
            em.persist(category);
        }

    /** 메인 카테고리 조회(단건) **/
    public StyleMainCategory findOne(Long id) {
            return em.find(StyleMainCategory.class, id);
        }

    /** 메인 카테고리 조회(전체) **/
    public List<StyleMainCategory> findAll() {
        return em.createQuery("select smc from StyleMainCategory smc", StyleMainCategory.class)
                .getResultList();
    }

    /** 메인 카테고리 이름 조회 **/
    public StyleMainCategory findByName(String name) {
        return em.createQuery("select smc from StyleMainCategory smc where smc.name = :name", StyleMainCategory.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
