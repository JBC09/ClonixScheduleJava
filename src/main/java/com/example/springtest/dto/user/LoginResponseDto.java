package com.example.springtest.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private String message;

    public LoginResponseDto(String token, String message) {
        this.token = token;
        this.message = message;
    }

}
