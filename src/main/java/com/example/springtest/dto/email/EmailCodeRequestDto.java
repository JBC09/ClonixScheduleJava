package com.example.springtest.dto.email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class EmailCodeRequestDto {
    private String email;
    private String code;

    EmailCodeRequestDto(String email, String code) {
        this.email = email;
        this.code = code;
    }

}
