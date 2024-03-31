package com.example.hairshop.repository;

import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleMainCategory;
import com.example.hairshop.domain.StyleSubCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class StyleSubCategoryRepository {

    private final EntityManager em;

    /** 서브 카테고리 생성 **/
    public void save(StyleSubCategory category) {
        em.persist(category);
    }

    /** 서브 카테고리 조회(단건) **/
    public StyleSubCategory findOne(Long id) {
        return em.find(StyleSubCategory.class, id);
    }

    /** 서브 카테고리 조회(전체) **/
    public List<StyleSubCategory> findAll() {
        return em.createQuery("select ssc from StyleSubCategory ssc", StyleSubCategory.class)
                .getResultList();
    }

    /** 메인 카테고리 이름으로 서브 카테고리 조회 **/
    public List<StyleSubCategory> findByMainCategoryName(String name) {
        return em.createQuery("select ssc from StyleSubCategory ssc where ssc.mainCategory = :name", StyleSubCategory.class)
                .setParameter("name", name)
                .getResultList();
    }

    /** 서브 카테고리 이름 조회 **/
    public StyleSubCategory findByName(String name) {
        return em.createQuery("select ssc from StyleSubCategory ssc where ssc.name = :name", StyleSubCategory.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    /** 서브 카테고리의 스타일 조회 **/
    public List<Style> findStyle(String name) {
        return em.createQuery("select ssc.styles from StyleSubCategory ssc where ssc.name = :name", Style.class)
                .setParameter("name", name)
                .getResultList();
    }
}
