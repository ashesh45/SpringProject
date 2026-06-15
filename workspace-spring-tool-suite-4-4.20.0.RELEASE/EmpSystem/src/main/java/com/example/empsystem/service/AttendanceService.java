package com.example.empsystem.service;

import java.util.List;

import com.example.empsystem.model.Attendance;

public interface AttendanceService {

    String checkIn(Long id);

    String checkOut(Long id);

    List<Attendance> getAllAttendance();

    List<Attendance> getAllAttendanceSorted();

    List<Attendance> getAttendanceByEmployee(Long id);

    Attendance getTodayAttendance(Long id);

}
