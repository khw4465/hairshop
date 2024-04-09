package com.example.hairshop.service;

import com.example.hairshop.domain.User;
import com.example.hairshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User join(User user) {
        Optional<User> findUser = userRepository.findByKakao(user.getKakaoId());
        if (findUser.isEmpty()){
            userRepository.save(user);
            return user;
        }
        return findUser.get();
    }

}
