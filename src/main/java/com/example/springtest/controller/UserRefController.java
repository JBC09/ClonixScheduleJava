package com.example.springtest.controller;

import com.example.springtest.dto.UserDto;
import com.example.springtest.service.UserService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRefController {

    private final UserService userService;

    public UserRefController(UserService userService) {
        this.userService = userService;
    }

    // 유저 회원 관리에 관환 API
    @PostMapping("/create")
    public UserDto create(@RequestBody UserDto user) {
        return userService.CreateUser(user);
    }

    @PostMapping("/login")
    public String login() {
        return "signIn";
    }

    @PostMapping("/logout")
    public String logout() {
        return "signOut";
    }

    // 유저 로그인 정보 가져오기
    @PostMapping("/getInfo")
    public UserDto getInfo(@RequestBody String userId) {
        return userService.GetUserInfo(userId);
    }
}
