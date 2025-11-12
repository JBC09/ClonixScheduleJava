package com.example.springtest.service;

import com.example.springtest.dto.email.EmailCodeRequestDto;
import com.example.springtest.dto.email.EmailRequestDto;
import com.example.springtest.dto.email.EmailValidateResponseDto;
import jakarta.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final codeCacheService codeCache;

    public boolean sendEmail(EmailRequestDto req) {
        try {
            log.info("send email to {} with subject {} and content {}", req.getTo(), req.getSubject(), req.getContent());


            SecureRandom random = new SecureRandom();
            String code = String.format("%06d", random.nextInt(1000000));
            // 이메일 Code 보내기
            MimeMessage message = mailSender.createMimeMessage();

            // ✅ HTML 본문 구성
            String html = """
                    <div style="font-family:Arial,sans-serif;line-height:1.6;padding:20px;border:1px solid #ddd;border-radius:8px;background:#fafafa;max-width:480px;margin:auto;">
                        <h2 style="color:#2c3e50;">이메일 인증 코드</h2>
                        <p>안녕하세요 👋 <b>Grass Schedule</b> 서비스에서 보낸 인증 메일입니다.</p>
                        <p>아래의 인증 코드를 입력해주세요:</p>
                        <div style="font-size:24px;font-weight:bold;color:#3498db;margin:20px 0;">%s</div>
                        <p>이 코드는 <b>3분간</b>만 유효합니다.</p>
                        <hr/>
                        <p style="font-size:12px;color:#777;">본 메일은 자동 발송되었습니다. 회신하지 마세요.</p>
                    </div>
                    """.formatted(code);

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");


            helper.setTo(req.getTo());
            helper.setSubject(req.getSubject());
            helper.setText(html, true);
            helper.setFrom("chanbin5634@gmail.com");
            mailSender.send(message);

            codeCache.putCode(req.getTo(), code);

            return true;
         }    catch(Exception e) {
            
            log.error("send email fail: " + e.getMessage());


            
            return false;
        }
    }

    public EmailValidateResponseDto validateEmail(EmailCodeRequestDto req) {
        if(codeCache.verifyCode(req.getEmail(), req.getCode())) {
            return new EmailValidateResponseDto(true, "이메일 인증에 성공하였습니다.");
        }
        else {
            return new EmailValidateResponseDto(false, "유효하지 않은 코드입니다.");
        }
    }

}
