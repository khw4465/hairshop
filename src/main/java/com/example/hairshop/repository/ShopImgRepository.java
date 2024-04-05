package com.example.hairshop.repository;

import com.example.hairshop.domain.ShopImg;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShopImgRepository {

    private final EntityManager em;

    public void save(ShopImg shopImg) {
        em.persist(shopImg);
    }

    public void delete(ShopImg shopImg) {
        em.remove(shopImg);
    }
}
