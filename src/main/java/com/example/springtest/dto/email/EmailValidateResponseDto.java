package com.example.springtest.dto.email;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailValidateResponseDto {
    private boolean valid;
    private String message;

    public EmailValidateResponseDto(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }


}
