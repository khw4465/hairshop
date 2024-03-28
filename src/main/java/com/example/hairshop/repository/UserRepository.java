package com.example.hairshop.repository;

import com.example.hairshop.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    /** 카카오아이디 회원 조회 **/
    public Optional<User> findByKakao(String kakaoId) {
        try {
            User user = em.createQuery("select u from User u where u.kakaoId = :kakaoId", User.class)
                    .setParameter("kakaoId", kakaoId)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
