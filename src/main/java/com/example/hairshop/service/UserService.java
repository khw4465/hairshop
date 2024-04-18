package com.example.hairshop.service;

import com.example.hairshop.domain.User;
import com.example.hairshop.dto.UserDto;
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

    public UserDto findUser(Long id) {
        User user = userRepository.findOne(id);
        UserDto userDto = new UserDto(user);
        return userDto;
    }

    public UserDto findUserDtoByKakaoId(String id) {
        User user = userRepository.findByKakaoId(id);
        UserDto userDto = new UserDto(user);
        return userDto;
    }

    public User findUserByKakaoId(String id) {
        return userRepository.findByKakaoId(id);

    }
}
