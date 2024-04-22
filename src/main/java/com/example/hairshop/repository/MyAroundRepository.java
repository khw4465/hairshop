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
                .getResultList();
    }
}
