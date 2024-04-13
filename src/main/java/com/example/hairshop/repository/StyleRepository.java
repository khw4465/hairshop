package com.example.hairshop.repository;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleSubCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StyleRepository {

    private final EntityManager em;

    /** 스타일 생성 **/
    public void save(Style style) {
        em.persist(style);
    }

    /** 스타일 삭제 **/
    public void delete(Style style) {
        em.remove(style);
    }

    /** 스타일 조회(단건) **/
    public Style findOne(Long id) {
        return em.find(Style.class, id);
    }

    /** 스타일 조회(전체) **/
    public List<Style> findAll() {
        return em.createQuery("select s from Style s", Style.class)
                .getResultList();
    }

    /** 특정 디자이너 스타일 조회 **/
    public List<Style> findStyleByDesigner(Designer designer) {
        return em.createQuery("select s from Style s where s.designer = :designer", Style.class)
                .setParameter("designer", designer)
                .getResultList();
    }

    /** 서브 카테고리 스타일 조회 **/
    public List<Style> findStyleByCategory(StyleSubCategory category) {
        return em.createQuery("select s from Style s join fetch s.subCategorys sc where sc = :category", Style.class)
                .setParameter("category", category)
                .getResultList();
    }

    /** 이미지주소로 스타일 조회 **/
    public Optional<Style> findByImg(String imgUrl) {
        try {
            Style style = em.createQuery("select s from Style s where s.imgUrl = :imgUrl", Style.class)
                    .setParameter("imgUrl", imgUrl)
                    .getSingleResult();
            return Optional.of(style);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /** 스타일북 페이징 **/
    public List<Style> findPageAll(int offset, int limit) {
        return em.createQuery("select s from Style s", Style.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /** 스타일북 카운트 쿼리 **/
    public Long countQueryAll() {
        return em.createQuery("select count(s) from Style s", Long.class)
                .getSingleResult();
    }

    /** 스타일북 카테고리별 페이징 **/
    public List<Style> findPageByCategory(String categoryName, int offset, int limit) {
        return em.createQuery("select s from Style s join s.subCategorys sc where sc.name = :category", Style.class)
                .setParameter("category", categoryName)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /** 스타일북 카테고리별 카운트 쿼리 **/
    public Long countQueryByCategory(String categoryName) {
        return em.createQuery("select count(s) from Style s join s.subCategorys sc where sc.name = :category", Long.class)
                .setParameter("category", categoryName)
                .getSingleResult();
    }
}
