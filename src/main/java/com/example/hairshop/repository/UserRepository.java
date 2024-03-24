package com.example.hairshop.repository;

import com.example.hairshop.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    /** 회원 생성 **/
    public void save(User user) {
        em.persist(user);
    }

    /** 회원 조회(단건) **/
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    /** 회원 조회(전체) **/
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
