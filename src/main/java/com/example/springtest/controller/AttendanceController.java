package com.example.springtest.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    public AttendanceController() {
        
    }

    @RequestMapping("/regist")
    public void attendanceRegist() {}

    @RequestMapping("/getList")
    public void GetAttendanceList() {}
}
