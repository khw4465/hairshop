package com.example.hairshop.repository;

import com.example.hairshop.domain.Menu;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepository {

    private final EntityManager em;

    public void save(Menu menu) {
        em.persist(menu);
    }

    public void delete(Menu menu) {
        em.remove(menu);
    }
}
