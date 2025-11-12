package com.example.springtest.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class codeCacheService {
    private final Map<String, CodeEntry> codeCache = new ConcurrentHashMap<>();
    
    // 코드 저장 객체 (Code, Timestamp)
    private static class CodeEntry {
        String code;
        long timestamp;
        boolean isValid;

        CodeEntry(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
            this.isValid = false;
        }

        CodeEntry(String code, long timestamp, boolean isValid) {
            this.code = code;
            this.timestamp = timestamp;
            this.isValid = isValid;
        }
    }

    public void putCode(String email, String code) {
        codeCache.put(email, new CodeEntry(code, System.currentTimeMillis()));
    }

    public boolean verifyCode(String email, String code) {
        CodeEntry entry = codeCache.get(email);

        if (entry == null) {
            return false;
        }
        else {
            boolean valid = entry.code.equals(code) && (System.currentTimeMillis() - entry.timestamp) < 180000;

            if(valid) {
                codeCache.put(email, new CodeEntry("NONE", System.currentTimeMillis(), true));
            }

            return valid;
        }
    }

    public boolean isValidEmail(String email) {

        if(codeCache.get(email) == null) {
            return false;
        }

        return codeCache.get(email).isValid;
    }

    @Scheduled(fixedRate =  3 * 60 * 1000) // 3분마다 실행함
    public void clearExpiredCode() {
        long now = System.currentTimeMillis();
        codeCache.entrySet().removeIf(e ->
                now - e.getValue().timestamp > 3 * 60 * 1000 // 3분 경과시 삭제
        );
    }

}
