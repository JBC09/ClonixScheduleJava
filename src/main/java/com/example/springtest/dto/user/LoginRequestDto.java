package com.example.springtest.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    private String userId;
    private String userPw;

    public LoginRequestDto(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }
}
