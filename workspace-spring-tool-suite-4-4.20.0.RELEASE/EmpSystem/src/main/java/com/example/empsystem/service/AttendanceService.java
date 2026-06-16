package com.example.empsystem.service;

import java.util.List;

import com.example.empsystem.dto.AttendanceDto;

public interface AttendanceService {

    String checkIn(Long empId);

    String checkOut(Long empId);

    List<AttendanceDto> getMyAttendance(Long empId);

    AttendanceDto getTodayAttendance(Long empId);

    List<AttendanceDto> getAllAttendanceSorted();
}
