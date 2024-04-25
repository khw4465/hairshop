package com.example.hairshop.repository;

import com.example.hairshop.domain.StyleTip;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StyleTipRepository {

    private final EntityManager em;

    public void save(StyleTip styleTip) {
        em.persist(styleTip);
    }

    public List<StyleTip> findAll() {
        return em.createQuery("select st from StyleTip st order by st.id DESC", StyleTip.class)
                .getResultList();
    }
}
