package com.example.springtest.dto.email;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmailRequestDto {
    private String to;
    private String subject;
    private String content;

    public EmailRequestDto(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }
}
