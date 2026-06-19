package com.example.empsystem.service;

import java.util.List;

import com.example.empsystem.dto.AttendanceDto;

import jakarta.servlet.http.HttpServletResponse;

public interface AttendanceService {

    String checkIn(Long empId);

    String checkOut(Long empId);

    List<AttendanceDto> getMyAttendance(Long empId);

    AttendanceDto getTodayAttendance(Long empId);

    List<AttendanceDto> getAllAttendanceSorted();

    void exportExcel(HttpServletResponse response);
}
