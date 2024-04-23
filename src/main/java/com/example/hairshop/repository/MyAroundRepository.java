package com.example.hairshop.repository;

import com.example.hairshop.domain.MyAround;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyAroundRepository {

    private final EntityManager em;

    public List<MyAround> findAll() {
        return em.createQuery("select m from MyAround m", MyAround.class)
                .setMaxResults(50)
                .getResultList();
    }

    public List<MyAround> findByArea(double swLat, double neLat, double swLng, double neLng) {
        return em.createQuery("select m from MyAround m " +
                        "where m.x > :swLat and m.x < :neLat " +
                        "and m.y > :swLng and m.y < :neLng", MyAround.class)
                .setParameter("swLat", swLat)
                .setParameter("neLat", neLat)
                .setParameter("swLng", swLng)
                .setParameter("neLng", neLng)
                .setMaxResults(100)
                .getResultList();
    }
}
