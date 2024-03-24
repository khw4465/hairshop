package com.example.hairshop.repository;

import com.example.hairshop.domain.StyleCategory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StyleCategoryRepository {

    private final EntityManager em;

    /** 스타일 카테고리 생성 **/
    public void save(StyleCategory category) {
        em.persist(category);
    }

    /** 스타일 카테고리 조회(단건) **/
    public StyleCategory findOne(Long id) {
        return em.find(StyleCategory.class, id);
    }

    /** 스타일 카테고리 조회(전체) **/
    public List<StyleCategory> findAll() {
        return em.createQuery("select sc from StyleCategory sc", StyleCategory.class)
                .getResultList();
    }
}
