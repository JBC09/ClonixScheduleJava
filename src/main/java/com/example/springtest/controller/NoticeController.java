package com.example.springtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeController {

    public NoticeController() {
        log.info("Create NoticeController");
    }

    @GetMapping("/create")
    public void noticeRegist() {
        log.info("NoticeController regist");
    }

    @GetMapping("/getAllNotice")
    public void GetNoticeList() {
        log.info("NoticeController GetNoticeList");
    }


}
