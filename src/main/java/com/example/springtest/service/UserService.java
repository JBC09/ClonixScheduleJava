package com.example.springtest.service;

import com.example.springtest.dto.user.UserDto;
import com.example.springtest.entity.UserEntity;
import com.example.springtest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.info("Create UserService");
    }

    public UserDto GetUserInfo(String userId) {

        Optional<UserEntity> temp = userRepository.findByUserId(userId);
        if(temp.isPresent()) {
            log.info("Get User Info: " + temp.get());
            return new UserDto(temp.get());

        }
        else {
            log.info("User Not Found id: " + userId);
            return new UserDto();
        }
    }

    public boolean CheckUser(String userId, String userPw) {
        UserEntity temp = userRepository.findByUserId(userId).orElseThrow(()-> new UsernameNotFoundException("User Not Found id: " + userId));

        if(temp.getUserPw().equals(userPw)) {
            log.info("User Login Success id: " + userId);
            return true;
        }
        else {
            log.info("User Login Success id: " + userId, " UserPw Not Match" + userPw);
            return false;
        }
    }

    public boolean CreateUser(UserDto user){

        userRepository.save(user.toEntity());

        return true;
    }

}
