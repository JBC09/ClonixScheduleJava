package com.example.springtest.controller;

import com.example.springtest.config.JwtTokenProvider;
import com.example.springtest.dto.user.LoginRequestDto;
import com.example.springtest.dto.user.LoginResponseDto;
import com.example.springtest.dto.user.UserDto;
import com.example.springtest.dto.email.EmailCodeRequestDto;
import com.example.springtest.dto.email.EmailRequestDto;
import com.example.springtest.dto.email.EmailValidateResponseDto;
import com.example.springtest.service.EmailService;
import com.example.springtest.service.UserService;
import com.example.springtest.service.codeCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final codeCacheService codeCache;
    private final JwtTokenProvider jwtTokenProvider;

    // 유저 회원 관리에 관환 API
    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
        if(codeCache.isValidEmail(user.getUserId())) {
            userService.CreateUser(user);
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.badRequest().body(user);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {

        if(userService.CheckUser(loginRequest.getUserId(), loginRequest.getUserPw())) {
            return ResponseEntity.ok(new LoginResponseDto(jwtTokenProvider.generateToken(loginRequest.getUserId()), "로그인에 성공하였습니다."));
        }

        return ResponseEntity.ok(new LoginResponseDto("", "로그인에 실패하였습니다."));
    }

    @PostMapping("/logout")
    public boolean logout() {
        return true;
    }

    // 유저 로그인 정보 가져오기
    @GetMapping("/getInfo")
    public UserDto getInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return userService.GetUserInfo(jwtTokenProvider.validateAndGetUserId(token));
    }

    @PostMapping("/mail/sendCode")
    public boolean sendEmailCode(@RequestBody EmailRequestDto emailObject) {

        if(userService.GetUserInfo(emailObject.getTo()) != new UserDto()) {
            return false;
        }
        return emailService.sendEmail(emailObject);
    }

    // 유저 이메일 인증 가져오기
    @PostMapping("/mail/validate")
    public EmailValidateResponseDto validateEmail(@RequestBody EmailCodeRequestDto emailObject) {
        return emailService.validateEmail(emailObject);
    }
}
