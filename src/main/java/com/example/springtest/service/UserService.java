package com.example.springtest.service;

import com.example.springtest.dto.UserDto;
import com.example.springtest.entity.UserEntity;
import com.example.springtest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.info("Create UserService");
    }

    public UserDto GetUserInfo(String userId) {
        return new UserDto(userRepository.findByUserName(userId));
    }

    public UserDto CreateUser(UserDto user){

        log.info("Create User: " + user);

        userRepository.save(user.toEntity());

        return user;
    }
}
